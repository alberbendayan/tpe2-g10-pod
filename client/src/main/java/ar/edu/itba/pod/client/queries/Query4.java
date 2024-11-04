package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.client.Query;
import ar.edu.itba.pod.client.csv.CSVwriter;
import ar.edu.itba.pod.client.models.City;
import collators.InfractionAndAmountCollator;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.InfractionAndAmountCombiner;
import keyPredicates.CheckAgency;
import mappers.InfractionAndAmountMapper;
import models.InfractionAndAgencyTotal;
import models.InfractionAndAmount;
import models.InfractionAndAmountStats;
import models.Ticket;
import reducers.InfractionAndAmountReducer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.SortedSet;
import java.util.stream.Stream;

public class Query4 extends Query {

    private static final String OUTPUT_HEADER = "Infraction;Min;Max;Diff\n";
    private final Integer n;
    private final String agency;
    private IMap<Ticket, InfractionAndAmount> ticketIMap;
    private IMap<String, String> infractionIMap;

    public Query4(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath, Integer n, String agency){
        super(hazelcastInstance, city, inPath, outPath, OUTPUT_HEADER);
        this.n = n;
        this.agency = agency;
        this.ticketIMap = hazelcastInstance.getMap("g10-tickets");
        this.infractionIMap = hazelcastInstance.getMap("g10-infractions");
    }

    @Override
    protected void readCSV() {
        try (Stream<String> infractions = Files.lines(Paths.get(inPath, "/infractions"+ city.getName()+ ".csv")).skip(1)) {
            infractions.forEach(line -> {
                String[] fields = line.split(";");
                infractionIMap.put(fields[0], fields[1]);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Stream<String> tickets = Files.lines(Paths.get(inPath, "/tickets"+ city.getName()+ ".csv")).skip(1)) {
            tickets.forEach(line -> {
                Ticket ticket = cityFormatter.formatTicket(line);
                ticketIMap.put(ticket, new InfractionAndAmount(ticket.getInfractionId(), ticket.getFineAmount(), infractionIMap.getOrDefault(ticket.getInfractionId(), null)));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeJob() {

        JobTracker jobTracker = hazelcastInstance.getJobTracker("g10-query4");
        Job<Ticket, InfractionAndAmount> job = jobTracker.newJob(KeyValueSource.fromMap(ticketIMap));
        ICompletableFuture<SortedSet<InfractionAndAmountStats>> future = job
                .keyPredicate(new CheckAgency(agency))
                .mapper(new InfractionAndAmountMapper())
                .combiner(new InfractionAndAmountCombiner())
                .reducer(new InfractionAndAmountReducer())
                .submit(new InfractionAndAmountCollator(n));

        try {
            SortedSet<InfractionAndAmountStats> result = future.get();
            CSVwriter<InfractionAndAgencyTotal> writer = new CSVwriter<>();
            writer.write(outPath + "/query4.csv", OUTPUT_HEADER, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
