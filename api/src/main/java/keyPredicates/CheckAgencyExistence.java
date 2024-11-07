package keyPredicates;

import com.hazelcast.mapreduce.KeyPredicate;
import models.AgencyYearMonth;
import models.InfractionAndAgency;
import models.Ticket;

import java.util.Set;

public class CheckAgencyExistence implements KeyPredicate<Ticket> {
    private final Set<String> validAgencies;
    public CheckAgencyExistence(Set<String> validAgencies) {
        this.validAgencies = validAgencies;
    }

    @Override
    public boolean evaluate(Ticket ticket) {
        return validAgencies.contains(ticket.getIssuingAgency());
    }

}
