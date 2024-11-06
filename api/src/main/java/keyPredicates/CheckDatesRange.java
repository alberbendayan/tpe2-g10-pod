package keyPredicates;

import com.hazelcast.mapreduce.KeyPredicate;
import models.CountyPlateInfractionAndDate;
import models.Ticket;

import java.time.LocalDate;

public class CheckDatesRange implements KeyPredicate<CountyPlateInfractionAndDate> {

    LocalDate from;
    LocalDate to;
    public CheckDatesRange(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }
    @Override
    public boolean evaluate(CountyPlateInfractionAndDate countyAndDate) {
        return countyAndDate.getDate().isAfter(from) && countyAndDate.getDate().isBefore(to);
    }


}
