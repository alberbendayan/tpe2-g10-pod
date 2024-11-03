package ar.edu.itba.pod.client.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class CSVwriter<T> {
    public void write(String filename, String header, Set<T> data) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(header + "\n");
            for (T element : data) {
                writer.write(element.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
