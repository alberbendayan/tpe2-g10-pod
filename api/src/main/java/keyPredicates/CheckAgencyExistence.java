package keyPredicates;

import com.hazelcast.mapreduce.KeyPredicate;
import models.AgencyYearMonth;
import models.InfractionAndAgency;

import java.util.Set;

public class CheckAgencyExistence implements KeyPredicate<AgencyYearMonth> {
    private final Set<String> validAgencies;
    public CheckAgencyExistence(Set<String> validAgencies) {
        this.validAgencies = validAgencies;
    }

    @Override
    public boolean evaluate(AgencyYearMonth agencyYearMonth) {
        return validAgencies.contains(agencyYearMonth.getAgency());
    }

}
