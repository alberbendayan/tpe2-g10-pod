package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import models.InfractionAndAgency;
import models.InfractionAndAgencyTotal;

public class InfractionAndAgencyReducer implements ReducerFactory<InfractionAndAgency, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(InfractionAndAgency key) {
        return new InfractionAndAgencyTotalReducer();
    }

    private static class InfractionAndAgencyTotalReducer extends Reducer<Long, Long> {
        private Long sum = 0L;

        @Override
        public void reduce(Long value) {
            sum += value;
        }

        @Override
        public Long finalizeReduce() {
            return sum;
        }
    }

}
