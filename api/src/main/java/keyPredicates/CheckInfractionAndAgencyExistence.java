package keyPredicates;

import com.hazelcast.mapreduce.KeyPredicate;
import models.InfractionAndAgency;
import models.Ticket;

import java.util.Set;

public class CheckInfractionAndAgencyExistence implements KeyPredicate<Ticket> {

    private final Set<String> validAgencies;
    private final Set<String> validInfractions;

    public CheckInfractionAndAgencyExistence(Set<String> validAgencies, Set<String> validInfractions) {
        this.validAgencies = validAgencies;
        this.validInfractions = validInfractions;
    }

    @Override
    public boolean evaluate(Ticket ticket) {
        return validAgencies.contains(ticket.getIssuingAgency()) && validInfractions.contains(ticket.getInfractionId());
    }
}