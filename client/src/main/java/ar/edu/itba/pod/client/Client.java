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

public class Client {
    protected static Logger logger = LoggerFactory.getLogger(Client.class);
    protected static final String CLUSTER_NAME = "g10";
    protected static final String CLUSTER_PASSWORD = "pass";


    public static void main(String[] args) throws InterruptedException {


        GroupConfig groupConfig = new GroupConfig().setName(CLUSTER_NAME).setPassword(CLUSTER_PASSWORD);
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setGroupConfig(groupConfig);


        clientConfig.setGroupConfig(groupConfig);
        ClientNetworkConfig clientNetworkConfig = new ClientNetworkConfig();

        // TODO: PONER IP DEL CLIENTE
        //clientNetworkConfig.addAddress(addresses);
        clientConfig.setNetworkConfig(clientNetworkConfig);


        HazelcastInstance hazelcastInstance = HazelcastClient.newHazelcastClient(clientConfig);

        switch(System.getProperty("query")) {
            case "1":
                new Query1(hazelcastInstance, City.fromString(System.getProperty("city")), System.getProperty("inPath"), System.getProperty("outPath"));
                break;
            case "2":
                new Query2(hazelcastInstance, City.fromString(System.getProperty("city")), System.getProperty("inPath"), System.getProperty("outPath"));
                break;
            case "3":
                new Query3(hazelcastInstance, City.fromString(System.getProperty("city")), System.getProperty("inPath"), System.getProperty("outPath"));
                break;
            case "4":
                new Query4(hazelcastInstance, City.fromString(System.getProperty("city")), System.getProperty("inPath"), System.getProperty("outPath"));
                break;
        }

    }
}
