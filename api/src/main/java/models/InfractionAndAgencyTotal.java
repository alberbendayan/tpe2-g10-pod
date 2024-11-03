package models;

import java.util.Objects;

public class InfractionAndAgencyTotal {
    private InfractionAndAgency infractionAndAgency;
    private Long total;

    public InfractionAndAgencyTotal(InfractionAndAgency infractionAndAgency, Long total) {
        this.infractionAndAgency = infractionAndAgency;
        this.total = total;
    }

    public InfractionAndAgency getInfractionAndAgency() {
        return infractionAndAgency;
    }

    public Long getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfractionAndAgencyTotal that)) return false;
        return Objects.equals(infractionAndAgency, that.infractionAndAgency) && Objects.equals(total, that.total);
    }

    @Override
    public String toString() {
        return infractionAndAgency.getInfraction()+";"+infractionAndAgency.getAgency()+";"+total;
    }

}
