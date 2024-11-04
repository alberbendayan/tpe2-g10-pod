package keyPredicates;

import com.hazelcast.mapreduce.KeyPredicate;
import models.Ticket;


public class CheckAgency implements KeyPredicate<Ticket> {

    private final String agency;

    public CheckAgency(String agency) {
        this.agency= agency;
    }

    @Override
    public boolean evaluate(Ticket ticket) {
        return ticket.getIssuingAgency().equals(agency);
    }

}
