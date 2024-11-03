package models;

import java.util.Objects;

public class AgencyYearMonthTotal {
    private AgencyYearMonth agencyYearMonth;
    private Long total;

    public AgencyYearMonthTotal(AgencyYearMonth agencyYearMonth, Long total) {
        this.agencyYearMonth = agencyYearMonth;
        this.total = total;
    }

    public AgencyYearMonth getAgencyYearMonth() {
        return agencyYearMonth;
    }
    public Long getTotal() {
        return total;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgencyYearMonthTotal that)) return false;
        return Objects.equals(agencyYearMonth, that.agencyYearMonth) && Objects.equals(total, that.total);
    }

    @Override
    public String toString() {
        return agencyYearMonth.toString()+";"+total;
    }
}
