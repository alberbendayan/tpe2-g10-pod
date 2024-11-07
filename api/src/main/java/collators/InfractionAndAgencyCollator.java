package collators;

import com.hazelcast.mapreduce.Collator;
import models.InfractionAndAgency;
import models.InfractionAndAgencyTotal;

import java.beans.Transient;
import java.util.*;
import java.util.logging.Logger;

public class InfractionAndAgencyCollator implements Collator<Map.Entry<InfractionAndAgency, Long>, SortedSet<InfractionAndAgencyTotal>>{


    private transient Logger logger = Logger.getLogger(InfractionAndAgencyCollator.class.getName());

    @Override
    public SortedSet<InfractionAndAgencyTotal> collate(Iterable<Map.Entry<InfractionAndAgency, Long>> values) {

        SortedSet<InfractionAndAgencyTotal> toReturn = new TreeSet<>(
                Comparator.comparing(InfractionAndAgencyTotal::getTotal, Comparator.reverseOrder()) // Orden descendente por total
                        .thenComparing(item -> item.getInfractionAndAgency().getInfractionName()) // Orden alfabético por infracción
                        .thenComparing(item -> item.getInfractionAndAgency().getAgency())     // Orden alfabético por agencia
        );
        logger.info("Collator hasNext: "+values.iterator().hasNext());
        for (Map.Entry<InfractionAndAgency, Long> entry : values) {
            logger.info("Value: "+entry.getValue());
            if(entry.getValue()!=0)
                toReturn.add(new InfractionAndAgencyTotal(entry.getKey(), entry.getValue()));
        }
        logger.info("Collator: "+toReturn.size());
        return toReturn;
    }
}
