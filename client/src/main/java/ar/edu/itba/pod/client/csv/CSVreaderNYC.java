package ar.edu.itba.pod.client.csv;

import models.Infraction;
import models.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class CSVreaderNYC extends CSVreader{
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<Ticket> getTickets(String inPath) {
        List<Ticket> tickets = new ArrayList<>();

        // TODO: regex
        try (BufferedReader br = new BufferedReader(new FileReader(inPath+"/ticketsNYC.csv"))) {
            String line = br.readLine();  // Skip header

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");

                String plate = fields[0];
                int infractionId = Integer.parseInt(fields[1]);
                double fineAmount = Double.parseDouble(fields[2]);
                String issuingAgency = fields[3];
                LocalDate issueDate = LocalDate.parse(fields[4], DATE_FORMAT);
                String countyName = fields[5];

                Ticket ticket = new Ticket(plate, String.valueOf(infractionId), fineAmount, issuingAgency, issueDate, countyName);
                tickets.add(ticket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    @Override
    public Set<String> getAgencies(String inPath) {
        return super.getAgenciesByPath(inPath+"/agenciesNYC.csv");
    }

    @Override
    public List<Infraction> getInfractions(String inPath) {
        return super.getInfractionsByPath(inPath+"/infractionsNYC.csv");
    }

}
