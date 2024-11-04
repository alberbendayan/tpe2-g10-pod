package collators;

import com.hazelcast.mapreduce.Collator;
import models.CountyInfractionAndPlate;
import models.CountyPercentage;

import java.util.*;

public class CountyCollator implements Collator<Map.Entry<CountyInfractionAndPlate, Long>, SortedSet<CountyPercentage>> {
    private int n;

    public CountyCollator(int n) {
        this.n = n;
    }

    @Override
    public SortedSet<CountyPercentage> collate(Iterable<Map.Entry<CountyInfractionAndPlate, Long>> iterable) {
        Map<String, Set<String>> totalPlatesPerCounty = new HashMap<>();
        Map<String, Set<String>> repeatedPlatesPerCounty = new HashMap<>();

        for (Map.Entry<CountyInfractionAndPlate, Long> entry : iterable) {
            CountyInfractionAndPlate cip = entry.getKey();
            String county = cip.getCounty();
            String plate = cip.getPlate();
            Long infractionsQty = entry.getValue();

            totalPlatesPerCounty.computeIfAbsent(county, k -> new HashSet<>()).add(plate);

            if (infractionsQty >= n) {
                repeatedPlatesPerCounty.computeIfAbsent(county, k -> new HashSet<>()).add(plate);
            }
        }

        SortedSet<CountyPercentage> result = new TreeSet<>(Comparator
                .comparing(CountyPercentage::getPercentage).reversed()  // Orden descendente por porcentaje
                .thenComparing(CountyPercentage::getCounty)             // Desempate alfabético por barrio
        );

        for (String county : totalPlatesPerCounty.keySet()) {
            Set<String> totalPlatesQty = totalPlatesPerCounty.get(county);
            Set<String> repeatedPlates = repeatedPlatesPerCounty.getOrDefault(county, Collections.emptySet());

            int totalPlatesCount = totalPlatesQty.size();
            int totalRepeatedPlatesCount = repeatedPlates.size();

            double repeatedPercentage = totalPlatesCount == 0 ? 0.0 : (100.0 * totalRepeatedPlatesCount / totalPlatesCount);

            if(repeatedPercentage > 0) {
                result.add(new CountyPercentage(county, repeatedPercentage));
            }
        }

        return result;
    }
}
