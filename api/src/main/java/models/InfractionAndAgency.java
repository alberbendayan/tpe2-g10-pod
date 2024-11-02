package models;

import java.util.Objects;

public class InfractionAndAgency {
    private String infraction;
    private String agency;


    public InfractionAndAgency(String infraction, String agency) {
        this.infraction = infraction;
        this.agency = agency;

    }

    public String getInfraction() {
        return infraction;
    }

    public String getAgency() {
        return agency;
    }

    @Override
    public String toString() {
        return "InfractionAndAgency{" +
                "infraction='" + infraction + '\'' +
                ", agency='" + agency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof InfractionAndAgency that)) return false;
        return infraction.equals(that.infraction) && agency.equals(that.agency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infraction, agency);
    }

}
