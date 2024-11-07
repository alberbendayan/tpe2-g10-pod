package reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;
import models.InfractionAndAgency;
import models.InfractionAndAgencyTotal;

import java.util.logging.Logger;

public class InfractionAndAgencyReducer implements ReducerFactory<InfractionAndAgency, Long, Long> {

    @Override
    public Reducer<Long, Long> newReducer(InfractionAndAgency key) {
        return new InfractionAndAgencyTotalReducer();
    }

    private static Logger logger = Logger.getLogger(InfractionAndAgencyReducer.class.getName());

    private static class InfractionAndAgencyTotalReducer extends Reducer<Long, Long> {
        private Long sum = 0L;

        @Override
        public void reduce(Long value) {
            logger.info("Reducer: "+value);
            System.out.println("Reducer: "+value);
            sum += value;
        }

        @Override
        public Long finalizeReduce() {
            return sum;
        }
    }

}
