package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.client.Query;
import ar.edu.itba.pod.client.csv.CSVwriter;
import ar.edu.itba.pod.client.models.City;
import collators.AgencyYearMonthCollator;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.AgencyYearMonthCombiner;
import keyPredicates.CheckAgencyExistence;
import mappers.AgencyYearMonthMapper;
import models.*;
import reducers.AgencyYearMonthReducer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Query2 extends Query {
    private IMap<Ticket, Long> ticketIMap;
    private Set<String> agencySet;

    private static final String OUTPUT_HEADER = "Agency;Year;Month;YTD";

    public Query2(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath){
        super(hazelcastInstance, city, inPath, outPath, OUTPUT_HEADER);
        this.ticketIMap = hazelcastInstance.getMap("g10-tickets-q2");
        this.agencySet = new HashSet<>();
    }

    @Override
    protected void readCSV() {
        try (Stream<String> tickets = Files.lines(Paths.get(inPath, "/tickets"+ city.getName()+ ".csv")).skip(1)) {
            tickets.forEach(line -> {
                Ticket ticket = cityFormatter.formatTicket(line);
                ticketIMap.put(ticket,ticket.getFineAmount());
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

        JobTracker jobTracker = hazelcastInstance.getJobTracker("g10-query2");
        Job<Ticket, Long> job = jobTracker.newJob(KeyValueSource.fromMap(ticketIMap));
        ICompletableFuture<SortedSet<AgencyYearMonthTotal>> future = job
                .keyPredicate(new CheckAgencyExistence(agencySet))
                .mapper(new AgencyYearMonthMapper())
                .combiner(new AgencyYearMonthCombiner())
                .reducer(new AgencyYearMonthReducer())
                .submit(new AgencyYearMonthCollator());

        try{
            SortedSet<AgencyYearMonthTotal> result = future.get();
            CSVwriter<AgencyYearMonthTotal> writer = new CSVwriter<>();
            writer.write(outPath+"/query2.csv", OUTPUT_HEADER, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ticketIMap.destroy();
        }
    }
}
