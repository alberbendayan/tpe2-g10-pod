package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.client.Query;
import ar.edu.itba.pod.client.csv.CSVwriter;
import ar.edu.itba.pod.client.models.City;
import collators.CountyCollator;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import combiners.CountyCombiner;
import keyPredicates.CheckDatesRange;
import mappers.CountyMapper;
import models.*;
import reducers.CountyReducer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.SortedSet;
import java.util.stream.Stream;

public class Query3 extends Query {

    private IMap<CountyPlateInfractionAndDate, Ticket> ticketIMap;
    private int n;
    private LocalDate from;
    private LocalDate to;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String OUTPUT_HEADER = "County;Percentage";

    public Query3(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath, int n, LocalDate from, LocalDate to) {
        super(hazelcastInstance, city, inPath, outPath, OUTPUT_HEADER,3);
        this.n = n;
        this.from = from;
        this.to = to;
        this.ticketIMap = hazelcastInstance.getMap("g10-tickets-q3");
    }
    @Override
    protected void readCSV() {
        try (Stream<String> tickets = Files.lines(Paths.get(inPath, "/tickets"+ city.getName()+ ".csv")).skip(1)) {
            tickets.forEach(line -> {
                Ticket ticket = cityFormatter.formatTicket(line);
                ticketIMap.put(new CountyPlateInfractionAndDate(ticket.getCountyName(),ticket.getIssueDate(),ticket.getPlate(),ticket.getInfractionId(),ticket.getId()), ticket);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void executeJob() {
        JobTracker jobTracker = hazelcastInstance.getJobTracker("g10-query3");
        Job<CountyPlateInfractionAndDate,Ticket> job = jobTracker.newJob(KeyValueSource.fromMap(ticketIMap));
        ICompletableFuture<SortedSet<CountyPercentage>> future = job
                .keyPredicate(new CheckDatesRange(from,to))
                .mapper(new CountyMapper())
                .combiner(new CountyCombiner())
                .reducer(new CountyReducer())
                .submit(new CountyCollator(n));

        try{
            SortedSet<CountyPercentage> result = future.get();
            CSVwriter<CountyPercentage> writer = new CSVwriter<>();
            writer.write(outPath+"/query3.csv", OUTPUT_HEADER, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ticketIMap.destroy();
        }
    }


}
