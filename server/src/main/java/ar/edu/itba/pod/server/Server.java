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

    public static void main(String[] args) throws InterruptedException, IOException {

        String network = null;  // Puede cambiarse a una dirección específica si es necesario
        List<String> memberAddresses = new ArrayList<>();
        String address = DEFAULT_ADDRESS;

        logger.info("hz-config Server Starting ...");
        Config config = new Config();
        GroupConfig groupConfig = new GroupConfig().setName(CLUSTER_NAME).setPassword(CLUSTER_PASSWORD);
        config.setGroupConfig(groupConfig);

        MulticastConfig multicastConfig = new MulticastConfig();
        JoinConfig joinConfig;
        TcpIpConfig tcpIpConfig;

        // Configuración de miembros del cluster
        if (!memberAddresses.isEmpty()) {
            multicastConfig.setEnabled(false);
            tcpIpConfig = new TcpIpConfig().setEnabled(true);
            memberAddresses.forEach(tcpIpConfig::addMember);
            joinConfig = new JoinConfig().setMulticastConfig(multicastConfig).setTcpIpConfig(tcpIpConfig);
        } else {
            multicastConfig.setEnabled(true);
            joinConfig = new JoinConfig().setMulticastConfig(multicastConfig);
        }

        // Configuración de interfaces de red
        InterfacesConfig interfacesConfig = new InterfacesConfig();
        if (network != null) {
            interfacesConfig.setInterfaces(Collections.singletonList(network)).setEnabled(true);
        } else {
            interfacesConfig.setEnabled(false); // Desactiva si no se necesita configurar una interfaz específica
        }

        NetworkConfig networkConfig = new NetworkConfig()
                .setInterfaces(interfacesConfig)
                .setJoin(joinConfig);
        if (address != null) {
            networkConfig.setPublicAddress(address);
        }
        config.setNetworkConfig(networkConfig);

        // Inicializar la instancia de Hazelcast
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
    }
}
