package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.client.Client;
import ar.edu.itba.pod.client.Query;
import ar.edu.itba.pod.client.csv.CSVwriter;
import ar.edu.itba.pod.client.models.City;
import collators.AgencyYearMonthCollator;
import collators.InfractionAndAgencyCollator;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.AgencyYearMonthCombiner;
import combiners.InfractionAndAgencyCombiner;
import keyPredicates.CheckAgencyExistence;
import keyPredicates.CheckInfractionAndAgencyExistence;
import mappers.AgencyYearMonthMapper;
import mappers.InfractionAndAgencyMapper;
import models.*;
import reducers.AgencyYearMonthReducer;
import reducers.InfractionAndAgencyReducer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Stream;

public class Query2 extends Query {
    private IMap<AgencyYearMonth, Long> ticketIMap;
    private IMap<String, String> agencyIMap;

    private static final String OUTPUT_HEADER = "Agency;Year;Month;YTD";

    public Query2(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath){
        super(hazelcastInstance, city, inPath, outPath, OUTPUT_HEADER);
        this.ticketIMap = hazelcastInstance.getMap("g10-tickets-q2");
        this.agencyIMap = hazelcastInstance.getMap("g10-agencies-q2");
    }

    @Override
    protected void readCSV() {
        try (Stream<String> tickets = Files.lines(Paths.get(inPath, "/tickets"+ city.getName()+ ".csv")).skip(1)) {
            tickets.forEach(line -> {
                Ticket ticket = cityFormatter.formatTicket(line);
                ticketIMap.put(new AgencyYearMonth(ticket.getIssuingAgency(),ticket.getIssueDate(),ticket.getId()),ticket.getFineAmount());
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

        JobTracker jobTracker = hazelcastInstance.getJobTracker("g10-query2");
        Job<AgencyYearMonth, Long> job = jobTracker.newJob(KeyValueSource.fromMap(ticketIMap));
        ICompletableFuture<SortedSet<AgencyYearMonthTotal>> future = job
                .keyPredicate(new CheckAgencyExistence(validAgencies))
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
        }
    }
}
