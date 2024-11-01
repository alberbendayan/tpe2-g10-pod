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
    private static final String DEFAULT_ADDRESS ="...";
    private static final String PUBLIC_ADDRESS ="...";
    public static void main(String[] args) throws InterruptedException, IOException {


        String network = null;
        List<String> memberAddresses = new ArrayList<>();
        String address = DEFAULT_ADDRESS;


        if(System.getProperty("address") != null){
            address= System.getProperty("address");
        }

        logger.info("hz-config Server Starting ...");
        Config config = new Config();
        GroupConfig groupConfig = new GroupConfig().setName(CLUSTER_NAME).setPassword(CLUSTER_PASSWORD);
        config.setGroupConfig(groupConfig);

        MulticastConfig multicastConfig = new MulticastConfig();
        JoinConfig joinConfig;
        TcpIpConfig tcpIpConfig;
        // agregamos los miembros
        if (!memberAddresses.isEmpty()) {
            multicastConfig.setEnabled(false);
            tcpIpConfig = new TcpIpConfig().setEnabled(true);
            memberAddresses.forEach(tcpIpConfig::addMember);
            joinConfig = new JoinConfig().setMulticastConfig(multicastConfig).setTcpIpConfig(tcpIpConfig);
        }
        else {
            multicastConfig.setEnabled(true);
            joinConfig = new JoinConfig().setMulticastConfig(multicastConfig);
        }
        InterfacesConfig interfacesConfig = new InterfacesConfig()
                .setInterfaces(Collections.singletonList(network))
                .setEnabled(true);
        NetworkConfig networkConfig = new NetworkConfig()
                .setInterfaces(interfacesConfig)
                .setJoin(joinConfig);
        if (address != null) {
            networkConfig.setPublicAddress(address);
        }
        config.setNetworkConfig(networkConfig);



        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);

    }}
