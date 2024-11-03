package collators;

import com.hazelcast.mapreduce.Collator;
import models.AgencyYearMonth;
import models.AgencyYearMonthTotal;
import models.InfractionAndAgency;
import models.InfractionAndAgencyTotal;

import java.util.*;

public class AgencyYearMonthCollator implements Collator<Map.Entry<AgencyYearMonth, Long>, SortedSet<AgencyYearMonthTotal>> {

    @Override
    public SortedSet<AgencyYearMonthTotal> collate(Iterable<Map.Entry<AgencyYearMonth, Long>> values) {
        Map<AgencyYearMonth, Long> sortedMap = new TreeMap<>(Comparator
                .comparing((AgencyYearMonth key) -> key.getAgency())
                .thenComparing(key -> key.getYear())
                .thenComparing(key -> key.getMonth())
        );

        for (Map.Entry<AgencyYearMonth, Long> entry : values) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        SortedSet<AgencyYearMonthTotal> result = new TreeSet<>(
                Comparator.comparing((AgencyYearMonthTotal item) -> item.getAgencyYearMonth().getAgency())
                .thenComparing(item -> item.getAgencyYearMonth().getYear())
                .thenComparing(item -> item.getAgencyYearMonth().getMonth()));
        long yearToDateSum = 0;
        String currentAgency = null;
        int currentYear = -1;

        for (Map.Entry<AgencyYearMonth, Long> entry : sortedMap.entrySet()) {
            AgencyYearMonth key = entry.getKey();

            if (!key.getAgency().equals(currentAgency) || key.getYear() != currentYear) {
                yearToDateSum = 0;
                currentAgency = key.getAgency();
                currentYear = key.getYear();
            }

            yearToDateSum += entry.getValue();
            if(yearToDateSum!=0)
                result.add(new AgencyYearMonthTotal(key, yearToDateSum));
        }
        return result;
    }
}
