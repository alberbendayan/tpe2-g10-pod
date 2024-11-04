package collators;

import com.hazelcast.mapreduce.Collator;
import models.InfractionAndAmountStats;


import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class InfractionAndAmountCollator implements Collator<Map.Entry<String, InfractionAndAmountStats>, SortedSet<InfractionAndAmountStats>> {

    private final Integer n;

    public InfractionAndAmountCollator(Integer n) {
        this.n = n;
    }

    @Override
    public SortedSet<InfractionAndAmountStats> collate(Iterable<Map.Entry<String, InfractionAndAmountStats>> values) {
        SortedSet<InfractionAndAmountStats> toReturn = new TreeSet<>(
                Comparator.comparing(InfractionAndAmountStats::getDifference, Comparator.reverseOrder()) // Orden descendente por diferencia
                        .thenComparing(InfractionAndAmountStats::getInfractionName) // Orden alfabético por infracción
        );

        for (Map.Entry<String, InfractionAndAmountStats> entry : values) {
            toReturn.add(entry.getValue());
            if (toReturn.size() == n) {
                return toReturn;
            }
        }
        return toReturn;
    }
}
