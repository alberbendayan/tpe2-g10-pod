package collators;

import com.hazelcast.mapreduce.Collator;
import models.InfractionAndAmountStats;


import java.util.*;
import java.util.logging.Logger;

public class InfractionAndAmountCollator implements Collator<Map.Entry<String, InfractionAndAmountStats>, SortedSet<InfractionAndAmountStats>> {

    private final Integer n;
    public InfractionAndAmountCollator(Integer n) {
        this.n = n;
    }

    @Override
    public SortedSet<InfractionAndAmountStats> collate(Iterable<Map.Entry<String, InfractionAndAmountStats>> values) {
        SortedSet<InfractionAndAmountStats> ordered = new TreeSet<>(
                Comparator.comparing(InfractionAndAmountStats::getDifference, Comparator.reverseOrder()) // Orden descendente por diferencia
                        .thenComparing(InfractionAndAmountStats::getInfractionName) // Orden alfabético por infracción
        );

        for (Map.Entry<String, InfractionAndAmountStats> entry : values) {
            ordered.add(entry.getValue());
        }
        SortedSet<InfractionAndAmountStats> toReturn = new TreeSet<>(
                Comparator.comparing(InfractionAndAmountStats::getDifference, Comparator.reverseOrder()) // Orden descendente por diferencia
                        .thenComparing(InfractionAndAmountStats::getInfractionName) // Orden alfabético por infracción
        );
        for (InfractionAndAmountStats stat: ordered) {
            toReturn.add(stat);
            if (toReturn.size() == n) {
                return toReturn;
            }
        }
        return toReturn;
    }
}
