package combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;
import models.InfractionAndAmount;
import models.InfractionAndAmountStats;

public class InfractionAndAmountCombiner implements CombinerFactory<String, InfractionAndAmount, InfractionAndAmountStats> {
    @Override
    public Combiner<InfractionAndAmount, InfractionAndAmountStats> newCombiner(String key) {
        return new InfractionAndAmountCombinerStats();
    }

    private static class InfractionAndAmountCombinerStats extends Combiner<InfractionAndAmount, InfractionAndAmountStats> {

        private Long minFine = Long.MAX_VALUE;
        private Long maxFine = Long.MIN_VALUE;
        private InfractionAndAmountStats stats = null;

        @Override
        public void combine(InfractionAndAmount value) {
            minFine = Math.min(minFine, value.getAmount());
            maxFine = Math.max(maxFine, value.getAmount());
            if(this.stats == null) {
                this.stats = new InfractionAndAmountStats(value.getInfractionName(), minFine, maxFine);
            } else {
                this.stats.setMinAmount(minFine);
                this.stats.setMaxAmount(maxFine);
            }
        }

        @Override
        public InfractionAndAmountStats finalizeChunk() {
            return stats;
        }
    }
}