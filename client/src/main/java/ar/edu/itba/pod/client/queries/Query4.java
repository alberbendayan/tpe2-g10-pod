package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.client.Client;
import ar.edu.itba.pod.client.Query;
import ar.edu.itba.pod.client.models.City;
import com.hazelcast.core.HazelcastInstance;

public class Query4 extends Query {

    private static final String OUTPUT_HEADER = "Infraction;Min;Max;Diff\n";
    private final String n;
    private final String agency;

    public Query4(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath, String n, String agency){
        super(hazelcastInstance, city, inPath, outPath, OUTPUT_HEADER);
        this.n = n;
        this.agency = agency;
    }
}
