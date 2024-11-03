package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.InfractionAndAgency;
import models.InfractionAndAgencyTotal;
import models.Ticket;

public class InfractionAndAgencyMapper implements Mapper<InfractionAndAgency, Ticket, InfractionAndAgency, Long> {

    private static final Long ONE = 1L;

    @Override
    public void map(InfractionAndAgency infractionAndAgency, Ticket ticket, Context<InfractionAndAgency, Long> context) {
        context.emit(infractionAndAgency, ONE);
    }
}
