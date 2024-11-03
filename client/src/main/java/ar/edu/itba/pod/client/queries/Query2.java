package ar.edu.itba.pod.client.queries;

import ar.edu.itba.pod.client.Client;
import ar.edu.itba.pod.client.Query;
import ar.edu.itba.pod.client.models.City;
import com.hazelcast.core.HazelcastInstance;

public class Query2 extends Query {

    private static final String OUTPUT_HEADER = "Agency;Year;Month;YTD\n";

    public Query2(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath){
        super(hazelcastInstance, city, inPath, outPath, OUTPUT_HEADER);
    }

    @Override
    protected void readCSV() {

    }

    @Override
    protected void executeJob() {

    }
}
