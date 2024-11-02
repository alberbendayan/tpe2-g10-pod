package ar.edu.itba.pod.client.csv;

import models.Infraction;
import models.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public abstract class CSVreader {
    public abstract List<Ticket> getTickets(String inPath);
    public abstract Set<String> getAgencies(String inPath);
    public abstract List<Infraction> getInfractions(String inPath);

    public Set<String> getAgenciesByPath(String completePath){
        Set<String> agenciesSet = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(completePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String agency = line.trim();  // Remueve espacios innecesarios
                if (!agency.isEmpty()) {
                    agenciesSet.add(agency);  // Agrega al Set solo si no está vacío
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return agenciesSet;
    }

    public List<Infraction> getInfractionsByPath(String completePath){
        List<Infraction> infractionsList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(completePath))) {
            String line = br.readLine();  // Lee la cabecera y la omite

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 2) {
                    try {
                        int infractionId = Integer.parseInt(fields[0].trim());
                        String definition = fields[1].trim();

                        Infraction infraction = new Infraction();
                        infraction.setInfractionId(infractionId);
                        infraction.setDefinition(definition);

                        infractionsList.add(infraction);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing infraction ID: " + fields[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infractionsList;
    }
}
