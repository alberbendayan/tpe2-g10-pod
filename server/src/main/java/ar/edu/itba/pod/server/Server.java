package ar.edu.itba.pod.server;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    private static Logger logger = LoggerFactory.getLogger(Server.class);
    private static final String CLUSTER_NAME = "g10";
    private static final String CLUSTER_PASSWORD = "pass";
    private static final String DEFAULT_ADDRESS = "127.0.0.1";
    private static final String PUBLIC_ADDRESS = "127.0.0.1";

    public static void main(String[] args) {
        logger.info(" Server Starting ...");

        String[] interfaces = System.getProperty("interfaces", "127.0.0.*").split(";");

        // Config
        Config config = new Config();

        // Group Config
        GroupConfig groupConfig = new GroupConfig().setName(CLUSTER_NAME).setPassword(CLUSTER_PASSWORD);
        config.setGroupConfig(groupConfig);

        // Network Config
        MulticastConfig multicastConfig = new MulticastConfig();

        JoinConfig joinConfig = new JoinConfig().setMulticastConfig(multicastConfig);

        InterfacesConfig interfacesConfig = new InterfacesConfig()
                .setInterfaces(List.of(interfaces))
                .setEnabled(true);

        NetworkConfig networkConfig = new NetworkConfig().setInterfaces(interfacesConfig).setJoin(joinConfig);

        config.setNetworkConfig(networkConfig);
        config.setInstanceName(CLUSTER_NAME);


        Hazelcast.newHazelcastInstance(config);
    }
}
