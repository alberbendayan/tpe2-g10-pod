package keyPredicates;

import com.hazelcast.mapreduce.KeyPredicate;
import models.CountyPlateInfractionAndDate;
import models.Ticket;

import java.time.LocalDate;

public class CheckDatesRange implements KeyPredicate<Ticket> {

    LocalDate from;
    LocalDate to;
    public CheckDatesRange(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }
    @Override
    public boolean evaluate(Ticket ticket) {
        return ticket.getIssueDate().isAfter(from) && ticket.getIssueDate().isBefore(to);
    }


}
