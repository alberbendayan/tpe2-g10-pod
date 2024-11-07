package reducers;


import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import models.InfractionAndAmount;
import models.InfractionAndAmountStats;

public class InfractionAndAmountReducer2 implements ReducerFactory<String, InfractionAndAmount, InfractionAndAmountStats> {
    @Override
    public Reducer<InfractionAndAmount, InfractionAndAmountStats> newReducer(String key) {
        return new InfractionAndAmountReducerStats();
    }

    private static class InfractionAndAmountReducerStats extends Reducer<InfractionAndAmount, InfractionAndAmountStats> {

        private InfractionAndAmountStats stats = null;
        private Long minFine = Long.MAX_VALUE;
        private Long maxFine = Long.MIN_VALUE;
        @Override
        public void reduce(InfractionAndAmount value) {
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
        public InfractionAndAmountStats finalizeReduce() {
            return stats;
        }
    }
}