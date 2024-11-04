package reducers;


import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import models.InfractionAndAmount;
import models.InfractionAndAmountStats;

public class InfractionAndAmountReducer implements ReducerFactory<String, InfractionAndAmountStats, InfractionAndAmountStats> {
    @Override
    public Reducer<InfractionAndAmountStats, InfractionAndAmountStats> newReducer(String key) {
        return new InfractionAndAmountReducerStats();
    }

    private static class InfractionAndAmountReducerStats extends Reducer<InfractionAndAmountStats, InfractionAndAmountStats> {

        private InfractionAndAmountStats stats = null;

        @Override
        public void reduce(InfractionAndAmountStats value) {
            if(this.stats == null) {
                this.stats = value;
            } else {
                this.stats.setMinAmount(Math.min(this.stats.getMinAmount(), value.getMinAmount()));
                this.stats.setMaxAmount(Math.max(this.stats.getMaxAmount(), value.getMaxAmount()));
            }
        }

        @Override
        public InfractionAndAmountStats finalizeReduce() {
            return stats;
        }
    }
}