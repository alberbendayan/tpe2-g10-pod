package collators;

import com.hazelcast.mapreduce.Collator;
import models.InfractionAndAgency;
import models.InfractionAndAgencyTotal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class InfractionAndAgencyCollator implements Collator<Map.Entry<InfractionAndAgency, Long>, SortedSet<InfractionAndAgencyTotal>>{

    @Override
    public SortedSet<InfractionAndAgencyTotal> collate(Iterable<Map.Entry<InfractionAndAgency, Long>> values) {
        SortedSet<InfractionAndAgencyTotal> toReturn = new TreeSet<>(
                Comparator.comparing(InfractionAndAgencyTotal::getTotal, Comparator.reverseOrder()) // Orden descendente por total
                        .thenComparing(item -> item.getInfractionAndAgency().getInfractionName()) // Orden alfabético por infracción
                        .thenComparing(item -> item.getInfractionAndAgency().getAgency())     // Orden alfabético por agencia
        );
        for (Map.Entry<InfractionAndAgency, Long> entry : values) {
            if(entry.getValue()!=0)
                toReturn.add(new InfractionAndAgencyTotal(entry.getKey(), entry.getValue()));
        }
        return toReturn;
    }
}
