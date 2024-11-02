package ar.edu.itba.pod.client.csv;

import models.Infraction;
import models.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CSVreaderCHI extends CSVreader {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<Ticket> getTickets(String inPath) {
        List<Ticket> tickets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inPath))) {
            String line = br.readLine();  // Lee la cabecera y la omite

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");

                try {
                    LocalDateTime issueDate = LocalDateTime.parse(fields[0].trim(), DATE_TIME_FORMAT);
                    String communityAreaName = fields[1];
                    String unitDescription = fields[2];
                    String licensePlateNumber = fields[3];
                    String violationCode = fields[4];
                    int fineAmount = Integer.parseInt(fields[5]);

                    Ticket ticket = new Ticket(licensePlateNumber,violationCode,fineAmount,unitDescription,issueDate.toLocalDate(),communityAreaName);

                    tickets.add(ticket);
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line + ", " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    @Override
    public Set<String> getAgencies(String inPath) {
        return super.getAgenciesByPath(inPath+"/agenciesCHI.csv");
    }

    @Override
    public List<Infraction> getInfractions(String inPath) {
        return super.getInfractionsByPath(inPath+"/infractionsCHI.csv");
    }

}
