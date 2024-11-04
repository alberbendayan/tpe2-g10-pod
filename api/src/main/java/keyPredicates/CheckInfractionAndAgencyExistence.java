package keyPredicates;

import com.hazelcast.mapreduce.KeyPredicate;
import models.InfractionAndAgency;

import java.util.Set;

public class CheckInfractionAndAgencyExistence implements KeyPredicate<InfractionAndAgency> {

    private final Set<String> validAgencies;
    private final Set<String> validInfractions;

    public CheckInfractionAndAgencyExistence(Set<String> validAgencies, Set<String> validInfractions) {
        this.validAgencies = validAgencies;
        this.validInfractions = validInfractions;
    }

    @Override
    public boolean evaluate(InfractionAndAgency infractionAndAgency) {
        return validAgencies.contains(infractionAndAgency.getAgency()) && validInfractions.contains(infractionAndAgency.getInfractionId()) && infractionAndAgency.getInfractionName() != null;
    }
}