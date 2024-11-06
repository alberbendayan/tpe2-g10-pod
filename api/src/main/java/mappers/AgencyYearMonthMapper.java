package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.AgencyYearMonth;
import models.InfractionAndAgency;
import models.Ticket;

public class AgencyYearMonthMapper implements Mapper<Ticket,AgencyYearMonth, AgencyYearMonth, Long> {

    @Override
    public void map(Ticket ticket,AgencyYearMonth agencyYearMonth, Context<AgencyYearMonth, Long> context) {
        context.emit(agencyYearMonth, ticket.getFineAmount());
    }
}
