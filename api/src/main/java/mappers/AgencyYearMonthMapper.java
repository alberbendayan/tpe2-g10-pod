package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.AgencyYearMonth;
import models.InfractionAndAgency;
import models.Ticket;

public class AgencyYearMonthMapper implements Mapper<Ticket, Long, AgencyYearMonth, Long> {

    @Override
    public void map(Ticket ticket, Long amount, Context<AgencyYearMonth, Long> context) {
        context.emit(new AgencyYearMonth(ticket.getIssuingAgency(),ticket.getIssueDate()), amount);
    }
}
