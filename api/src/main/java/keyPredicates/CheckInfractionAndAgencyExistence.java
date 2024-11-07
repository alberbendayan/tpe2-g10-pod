package keyPredicates;

import collators.InfractionAndAgencyCollator;
import com.hazelcast.mapreduce.KeyPredicate;
import models.InfractionAndAgency;
import models.Ticket;

import java.util.Set;
import java.util.logging.Logger;

public class CheckInfractionAndAgencyExistence implements KeyPredicate<InfractionAndAgency> {

    private transient Logger logger = Logger.getLogger(CheckInfractionAndAgencyExistence.class.getName());

    private final Set<String> validAgencies;
    private final Set<String> validInfractions;

    public CheckInfractionAndAgencyExistence(Set<String> validAgencies, Set<String> validInfractions) {
        this.validAgencies = validAgencies;
        this.validInfractions = validInfractions;
    }

    @Override
    public boolean evaluate(InfractionAndAgency infractionAndAgency) {
        System.out.println("Todo: "+(validAgencies.contains(infractionAndAgency.getAgency())
                && validInfractions.contains(infractionAndAgency.getInfractionId())
                && infractionAndAgency.getInfractionName() != null));

        logger.info("Agency: "+validAgencies.contains(infractionAndAgency.getAgency()));
        logger.info("Infraction: "+validInfractions.contains(infractionAndAgency.getInfractionId()));
        System.out.println("Agency: "+validAgencies.contains(infractionAndAgency.getAgency()));
        System.out.println("Infraction: "+validInfractions.contains(infractionAndAgency.getInfractionId()));
        System.out.println("Last: "+infractionAndAgency.getInfractionName() != null);
        return validAgencies.contains(infractionAndAgency.getAgency())
                && validInfractions.contains(infractionAndAgency.getInfractionId())
                && infractionAndAgency.getInfractionName() != null;
    }

}