package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.client.Query;
import ar.edu.itba.pod.client.csv.CSVwriter;
import ar.edu.itba.pod.client.models.City;
import collators.InfractionAndAgencyCollator;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.InfractionAndAgencyCombiner;
import keyPredicates.CheckInfractionAndAgencyExistence;
import mappers.InfractionAndAgencyMapper;
import models.InfractionAndAgency;
import models.InfractionAndAgencyTotal;
import models.Ticket;
import reducers.InfractionAndAgencyReducer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

public class Query1 extends Query {

    private IMap<Ticket, InfractionAndAgency> ticketIMap;
    private IMap<String, String> infractionIMap;
    private Set<String> agencySet;

    private static final String OUTPUT_HEADER = "Infraction;Agency;Tickets";

    public Query1(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath) {
        super(hazelcastInstance, city, inPath, outPath, OUTPUT_HEADER, 1);
        this.ticketIMap = hazelcastInstance.getMap("g10-tickets-q1");
        this.infractionIMap = hazelcastInstance.getMap("g10-infractions-q1");
        this.agencySet = new HashSet<>();
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
                ticketIMap.put(ticket, new InfractionAndAgency(ticket.getInfractionId(), ticket.getIssuingAgency(), infractionIMap.getOrDefault(ticket.getInfractionId(), null)));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Stream<String> agencies = Files.lines(Paths.get(inPath, "/agencies"+ city.getName()+ ".csv")).skip(1)) {
            agencies.forEach(line -> {
                agencySet.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeJob() {

        Set<String> validInfractions = infractionIMap.keySet();

        JobTracker jobTracker = hazelcastInstance.getJobTracker("g10-query1");
        Job<Ticket, InfractionAndAgency> job = jobTracker.newJob(KeyValueSource.fromMap(ticketIMap));
        ICompletableFuture<SortedSet<InfractionAndAgencyTotal>> future = job
                .keyPredicate(new CheckInfractionAndAgencyExistence(agencySet, validInfractions))
                .mapper(new InfractionAndAgencyMapper())
                .combiner(new InfractionAndAgencyCombiner())
                .reducer(new InfractionAndAgencyReducer())
                .submit(new InfractionAndAgencyCollator());

        try {
            SortedSet<InfractionAndAgencyTotal> result = future.get();
            CSVwriter<InfractionAndAgencyTotal> writer = new CSVwriter<>();
            writer.write(outPath + "/query1.csv", OUTPUT_HEADER, result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            ticketIMap.destroy();
            infractionIMap.destroy();
        }


    }

}
