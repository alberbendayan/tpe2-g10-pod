package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.client.Query;
import ar.edu.itba.pod.client.csv.CSVwriter;
import ar.edu.itba.pod.client.models.City;
import collators.InfractionAndAgencyCollator;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Query1 extends Query {

    private Logger logger = Logger.getLogger(Query1.class.getName());
    private MultiMap<InfractionAndAgency,Ticket> ticketMMap;
    private IMap<String, String> infractionIMap;
    private IMap<String, String> agencyIMap;

    private static final String OUTPUT_HEADER = "Infraction;Agency;Tickets";

    public Query1(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath) {
        super(hazelcastInstance, city, inPath, outPath, OUTPUT_HEADER);
        this.ticketMMap = hazelcastInstance.getMultiMap("g10-tickets-q1a");
        this.infractionIMap = hazelcastInstance.getMap("g10-infractions-q1");
        this.agencyIMap = hazelcastInstance.getMap("g10-agencies-q1");
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
                if(!ticketMMap.put(new InfractionAndAgency(ticket.getInfractionId(),
                        ticket.getIssuingAgency(),
                        infractionIMap.getOrDefault(ticket.getInfractionId(),"")),ticket))
                    throw new IllegalStateException("Error adding ticket to multimap");

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Stream<String> agencies = Files.lines(Paths.get(inPath, "/agencies"+ city.getName()+ ".csv")).skip(1)) {
            agencies.forEach(line -> {
                agencyIMap.put(line, line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeJob() {

        Set<String> validAgencies = agencyIMap.keySet();
        Set<String> validInfractions = infractionIMap.keySet();


        JobTracker jobTracker = hazelcastInstance.getJobTracker("g10-query1");

        KeyValueSource k = KeyValueSource.fromMultiMap(ticketMMap);
        logger.info(k.toString());

        Job<InfractionAndAgency,Ticket> job = jobTracker.newJob(k);
        logger.info("Length of key: "+ticketMMap.keySet().size());
        logger.info("Length of key: "+ticketMMap.size());
        ICompletableFuture<SortedSet<InfractionAndAgencyTotal>> future = job
                .keyPredicate(new CheckInfractionAndAgencyExistence(validAgencies, validInfractions))
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
        }


    }

}
