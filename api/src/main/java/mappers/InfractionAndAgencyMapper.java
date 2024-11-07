package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.InfractionAndAgency;
import models.InfractionAndAgencyTotal;
import models.Ticket;
import java.util.logging.Logger;
public class InfractionAndAgencyMapper implements Mapper<InfractionAndAgency, Ticket, InfractionAndAgency, Long> {
    private static final Long ONE = 1L;
    private transient Logger logger = Logger.getLogger(InfractionAndAgencyMapper.class.getName());

    @Override
    public void map(InfractionAndAgency infractionAndAgency, Ticket ticket, Context<InfractionAndAgency, Long> context) {
        logger.info("Mapper: "+infractionAndAgency.toString());
        System.out.println("Mapper: "+infractionAndAgency.toString());
        context.emit(infractionAndAgency, ONE);
    }
}
