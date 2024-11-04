package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import models.CountyInfractionAndPlate;

public class CountyReducer implements ReducerFactory<CountyInfractionAndPlate,Long,Long> {
    @Override
    public Reducer<Long, Long> newReducer(CountyInfractionAndPlate countyInfractionAndPlate) {
        return new CountyTotalReducer();
    }

    private static class CountyTotalReducer extends Reducer<Long, Long> {
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
