package ar.edu.itba.pod.client.csv;

import models.Ticket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatterCHI implements Formatter {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public Ticket formatTicket(String line) {
        String[] fields=line.split(";");
        if(fields.length==6){
            try{
                LocalDateTime issueDate = LocalDateTime.parse(fields[0].trim(), DATE_TIME_FORMAT);
                String communityAreaName = fields[1];
                String unitDescription = fields[2];
                String licensePlateNumber = fields[3];
                String violationCode = fields[4];
                Long fineAmount = Math.round(Double.parseDouble(fields[5]));
                return new Ticket(licensePlateNumber,violationCode,fineAmount,unitDescription,issueDate.toLocalDate(),communityAreaName);
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
