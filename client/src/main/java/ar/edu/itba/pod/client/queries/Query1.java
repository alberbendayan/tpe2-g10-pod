package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.client.Query;
import ar.edu.itba.pod.client.models.City;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import models.InfractionAndAgency;
import models.Ticket;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Query1 extends Query {

    private static final String OUTPUT_HEADER = "Infraction;Agency;Tickets\n";

    public Query1(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath){
        super(hazelcastInstance, city, inPath, outPath, OUTPUT_HEADER);
    }

    @Override
    protected void readCSV(){
        IMap<InfractionAndAgency,Ticket> ticketIMap= hazelcastInstance.getMap("g10-tickets");
        IMap<String,String> infractionIMap= hazelcastInstance.getMap("g10-infractions");
        IMap<String,String> agencyIMap= hazelcastInstance.getMap("g10-agencies");
        try (Stream<String> tickets=Files.lines(Paths.get(inPath,"/tickets",city.getName(),".csv")).skip(1))
        {
            tickets.forEach(line->{
            Ticket ticket = cityFormatter.formatTicket(line);
            ticketIMap.put(new InfractionAndAgency(ticket.getInfractionId(),ticket.getIssuingAgency()),ticket);
        });
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        try (Stream<String> infractions=Files.lines(Paths.get(inPath,"/infractions",city.getName(),".csv")).skip(1))
        {
            infractions.forEach(line->{
                String[] fields=line.split(";");
                infractionIMap.put(fields[0],fields[1]);
            });
        }
        catch(IOException e)
        {
            e.printStackTrace();

        }
try (Stream<String> agencies=Files.lines(Paths.get(inPath,"/agencies",city.getName(),".csv")).skip(1))
        {
            agencies.forEach(line->{
                agencyIMap.put(line,line);
            });
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
