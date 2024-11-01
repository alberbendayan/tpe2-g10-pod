package ar.edu.itba.pod.client;

import ar.edu.itba.pod.client.models.City;
import com.hazelcast.core.HazelcastInstance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public abstract class Query {

    protected HazelcastInstance hazelcastInstance;
    protected City city;
    protected String inPath;
    protected String outPath;
    protected String outputHeader;
    protected FileWriter writer;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSSS");

    public Query(HazelcastInstance hazelcastInstance, City city, String inPath, String outPath, String outputHeader) {
        this.hazelcastInstance = hazelcastInstance;
        this.city = city;
        this.inPath = inPath;
        this.outPath = outPath;
        this.outputHeader = outputHeader;

    }

    public void startQuery(){
        //create Time File
        try {
            this.writer = new FileWriter(outPath + "/time.txt");
        } catch(IOException e){
            System.out.println("Error creating time file");
        }


        writeTimeFile("Inicio de la lectura del archivo");
        writeTimeFile("Fin de lectura del archivo");
        writeTimeFile("Inicio del trabajo map/reduce");
        writeTimeFile("Fin del trabajo map/reduce");


        //close Time File
        try {
            writer.close();
        } catch(IOException e){
            System.out.println("Error closing time file");
        }

    }

    protected void writeTimeFile(String string){
        try {
            writer.write(formatter.format(Instant.now()) + " INFO - " + string + "\n");
        }
        catch (IOException e){
            System.out.println("Error writing time file");
        }
    }
}
