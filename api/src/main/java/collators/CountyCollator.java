package collators;

import com.hazelcast.mapreduce.Collator;
import models.CountyInfractionAndPlate;
import models.CountyPercentage;

import java.util.Map;
import java.util.SortedSet;

public class CountyCollator implements Collator<Map.Entry<CountyInfractionAndPlate,Long>, SortedSet<CountyPercentage>> {
    private int n;

    public CountyCollator(int n) {
        this.n = n;
    }
    @Override
    public SortedSet<CountyPercentage> collate(Iterable<Map.Entry<CountyInfractionAndPlate, Long>> iterable) {
        return null;
    }
}
