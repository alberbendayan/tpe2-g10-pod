package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import models.AgencyYearMonth;
import models.InfractionAndAgency;

public class AgencyYearMonthReducer implements ReducerFactory<AgencyYearMonth, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(AgencyYearMonth key) {
        return new AgencyYearMonthTotalReducer();
    }

    private static class AgencyYearMonthTotalReducer extends Reducer<Long, Long> {
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
