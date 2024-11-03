package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.AgencyYearMonth;
import models.InfractionAndAgency;
import models.Ticket;

public class AgencyYearMonthMapper implements Mapper<AgencyYearMonth, Long, AgencyYearMonth, Long> {

    @Override
    public void map(AgencyYearMonth agencyYearMonth, Long amount, Context<AgencyYearMonth, Long> context) {
        context.emit(agencyYearMonth, amount);
    }
}
