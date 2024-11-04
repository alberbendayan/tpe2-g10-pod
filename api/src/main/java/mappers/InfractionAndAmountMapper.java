package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.InfractionAndAgency;
import models.InfractionAndAmount;
import models.InfractionAndAmountStats;
import models.Ticket;

public class InfractionAndAmountMapper implements Mapper<Ticket, InfractionAndAmount, String, InfractionAndAmount> {

    @Override
    public void map(Ticket ticket, InfractionAndAmount infractionAndAmount, Context<String, InfractionAndAmount> context) {
        context.emit(ticket.getInfractionId(), infractionAndAmount);
    }
}