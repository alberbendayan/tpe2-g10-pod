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
import keyPredicates.CheckAgencyAndInfraction;
import mappers.InfractionAndAmountMapper;
import models.Infraction;
import models.InfractionAndAmount;
import models.InfractionAndAmountStats;
import models.Ticket;
import reducers.InfractionAndAmountReducer;
import reducers.InfractionAndAmountReducer2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

public class Query4 extends Query {

    private static final String OUTPUT_HEADER = "Infraction;Min;Max;Diff";
    private final Integer n;
    private final String agency;
    private IMap<Ticket, InfractionAndAmount> ticketIMap;
    private IMap<String, String> infractionIMap;


    public Query4(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath, Integer n, String agency){
        super(hazelcastInstance, city, inPath, outPath, OUTPUT_HEADER);
        this.n = n;
        this.agency = agency.replace('_',' ');
        this.ticketIMap = hazelcastInstance.getMap("g10-tickets-q4");
        this.infractionIMap = hazelcastInstance.getMap("g10-infractions-q4");
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
                ticketIMap.put(ticket, new InfractionAndAmount(ticket.getInfractionId(), ticket.getFineAmount(), infractionIMap.getOrDefault(ticket.getInfractionId(), "")));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeJob() {

        Set<String> validInfractions = infractionIMap.keySet();

        JobTracker jobTracker = hazelcastInstance.getJobTracker("g10-query4");
        Job<Ticket, InfractionAndAmount> job = jobTracker.newJob(KeyValueSource.fromMap(ticketIMap));
        ICompletableFuture<SortedSet<InfractionAndAmountStats>> future = job
                .keyPredicate(new CheckAgencyAndInfraction(agency,validInfractions))
                .mapper(new InfractionAndAmountMapper())
                .combiner(new InfractionAndAmountCombiner())
                .reducer(new InfractionAndAmountReducer())
//                .reducer(new InfractionAndAmountReducer2())
                .submit(new InfractionAndAmountCollator(n));

        // Si se quiere usar sin combiner, hay que cambiar el reducer a InfractionAndAmountReducer2
        try {
            SortedSet<InfractionAndAmountStats> result = future.get();
            CSVwriter<InfractionAndAmountStats> writer = new CSVwriter<>();
            writer.write(outPath + "/query4.csv", OUTPUT_HEADER, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ticketIMap.destroy();
            infractionIMap.destroy();
        }

    }
}
