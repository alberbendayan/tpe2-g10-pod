package ar.edu.itba.pod.client;


import ar.edu.itba.pod.client.models.City;
import ar.edu.itba.pod.client.queries.*;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Client {
    protected static Logger logger = LoggerFactory.getLogger(Client.class);
    protected static final String CLUSTER_NAME = "g10";
    protected static final String CLUSTER_PASSWORD = "pass";


    public static void main(String[] args) throws InterruptedException {

        //Group config
        GroupConfig groupConfig = new GroupConfig().setName(CLUSTER_NAME).setPassword(CLUSTER_PASSWORD);

        //Client Network Config
        ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();
        clientNetworkConfig.addAddress(System.getProperty("addresses").split(";"));

        // Client Config
        ClientConfig clientConfig = new ClientConfig().setGroupConfig(groupConfig).setNetworkConfig(clientNetworkConfig);

        // Node Client
        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);
        try {
            Query query = null;
            switch (System.getProperty("query")) {
                case "1":
                    query = new Query1(hazelcastInstance,
                            City.fromString(System.getProperty("city")),
                            System.getProperty("inPath"),
                            System.getProperty("outPath"));
                    break;
                case "2":
                    query = new Query2(hazelcastInstance,
                            City.fromString(System.getProperty("city")),
                            System.getProperty("inPath"),
                            System.getProperty("outPath"));
                    break;
                case "3":
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    query = new Query3(hazelcastInstance,
                            City.fromString(System.getProperty("city")),
                            System.getProperty("inPath"),
                            System.getProperty("outPath"),
                            Integer.parseInt(System.getProperty("n")),
                            LocalDate.parse(System.getProperty("from"), formatter),
                            LocalDate.parse(System.getProperty("to"), formatter));
                    break;
                case "4":
                    query = new Query4(hazelcastInstance,
                            City.fromString(System.getProperty("city")),
                            System.getProperty("inPath"),
                            System.getProperty("outPath"),
                            Integer.parseInt(System.getProperty("n")),
                            System.getProperty("agency"));
                    break;
            }

            query.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            hazelcastInstance.shutdown();
        }

    }
}
