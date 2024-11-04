package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import models.CountyInfractionAndPlate;
import models.InfractionAndAgency;

public class CountyCombiner implements CombinerFactory<CountyInfractionAndPlate,Long,Long> {
    @Override
    public Combiner<Long, Long> newCombiner(CountyInfractionAndPlate countyInfractionAndPlate) {
        return new CountyTotalCombiner();
    }


    private static class CountyTotalCombiner extends Combiner<Long, Long> {
        private Long sum = 0L;

        @Override
        public void combine(Long value) {
            sum += value;
        }

        @Override
        public Long finalizeChunk() {
            return sum;
        }

        @Override
        public void reset() {
            sum = 0L;
        }
    }


}
