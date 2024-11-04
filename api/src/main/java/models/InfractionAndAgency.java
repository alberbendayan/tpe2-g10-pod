package models;

import java.util.Objects;

public class InfractionAndAgency {
    private String infractionId;
    private String agency;
    private String infractionName;


    public InfractionAndAgency(String infractionId, String agency, String infractionName) {
        this.infractionId = infractionId;
        this.agency = agency;
        this.infractionName = infractionName;
    }

    public String getInfractionId() {
        return infractionId;
    }

    public String getInfractionName() {
        return infractionName;
    }

    public String getAgency() {
        return agency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfractionAndAgency that)) return false;
        return infractionId.equals(that.infractionId) && agency.equals(that.agency) && infractionName.equals(that.infractionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infractionId, agency, infractionName);
    }

    @Override
    public String toString() {
        return infractionName + ";" + agency;
    }
}
