package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import models.AgencyYearMonth;
import models.InfractionAndAgency;

public class AgencyYearMonthCombiner implements CombinerFactory<AgencyYearMonth, Long, Long> {
    @Override
    public Combiner<Long, Long> newCombiner(AgencyYearMonth key) {
        return new AgencyYearMonthTotalCombiner();
    }

    private static class AgencyYearMonthTotalCombiner extends Combiner<Long, Long> {
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
