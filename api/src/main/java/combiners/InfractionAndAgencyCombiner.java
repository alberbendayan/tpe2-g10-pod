package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import models.InfractionAndAgency;

public class InfractionAndAgencyCombiner implements CombinerFactory<InfractionAndAgency, Long, Long> {

        @Override
        public Combiner<Long, Long> newCombiner(InfractionAndAgency key) {
            return new InfractionAndAgencyTotalCombiner();
        }

        private static class InfractionAndAgencyTotalCombiner extends Combiner<Long, Long> {
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
