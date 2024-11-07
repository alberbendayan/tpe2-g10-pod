package keyPredicates;

import com.hazelcast.mapreduce.KeyPredicate;
import models.Ticket;

import java.util.Set;


public class CheckAgencyAndInfraction implements KeyPredicate<Ticket> {

    private final String agency;

    private final Set<String> validInfractions;

    public CheckAgencyAndInfraction(String agency, Set<String> validInfractions) {
        this.agency= agency;
        this.validInfractions = validInfractions;
    }

    @Override
    public boolean evaluate(Ticket ticket) {
        return ticket.getIssuingAgency().equals(agency) && validInfractions.contains(ticket.getInfractionId());
    }

}
