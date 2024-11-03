package ar.edu.itba.pod.client.csv;

import models.Ticket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatterNYC implements Formatter {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Ticket formatTicket(String line) {
        String[] fields = line.split(";");
        if (fields.length == 6) {
            try {
                String plate = fields[0];
                int infractionId = Integer.parseInt(fields[1]);
                double fineAmount = Double.parseDouble(fields[2]);
                String issuingAgency = fields[3];
                LocalDate issueDate = LocalDate.parse(fields[4], DATE_FORMAT);
                String countyName = fields[5];
                return new Ticket(plate, String.valueOf(infractionId), fineAmount, issuingAgency, issueDate, countyName);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
