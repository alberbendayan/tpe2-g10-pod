package models;

import java.io.Serializable;
import java.util.Objects;

public class CountyInfractionAndPlate implements Serializable {
    private static final long serialVersionUID = 1L;
    private String county;
    private String plate;
    private String infractionId;

    public CountyInfractionAndPlate(String county, String plate, String infractionId) {
        this.county = county;
        this.plate = plate;
        this.infractionId = infractionId;
    }

    public String getCounty() {
        return county;
    }

    public String getPlate() {
        return plate;
    }

    public String getInfractionId() {
        return infractionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(county, plate, infractionId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CountyInfractionAndPlate that)) return false;
        return county.equals(that.county) && plate.equals(that.plate) && infractionId.equals(that.infractionId);
    }
}
