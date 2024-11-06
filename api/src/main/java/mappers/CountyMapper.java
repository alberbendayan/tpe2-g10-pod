package mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;
import models.CountyInfractionAndPlate;
import models.CountyPlateInfractionAndDate;
import models.Ticket;

public class CountyMapper implements Mapper<CountyPlateInfractionAndDate,Ticket, CountyInfractionAndPlate,Long> {    private static final Long ONE = 1L;
    @Override
    public void map(CountyPlateInfractionAndDate countyPlateAndDate, Ticket ticket, Context<CountyInfractionAndPlate, Long> context) {
        context.emit(new CountyInfractionAndPlate(countyPlateAndDate.getCounty(), countyPlateAndDate.getPlate(),countyPlateAndDate.getInfractionId()),ONE);
    }
}
