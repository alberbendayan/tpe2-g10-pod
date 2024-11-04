package models;

import java.time.LocalDate;
import java.util.Objects;

public class CountyPlateInfractionAndDate {
    private String county;
    private String plate;
    private LocalDate date;

    private String infractionId;

    public CountyPlateInfractionAndDate(String county, LocalDate date, String plate, String infractionId) {
        this.county = county;
        this.date = date;
        this.plate = plate;
        this.infractionId = infractionId;
    }

    public String getCounty() {
        return county;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPlate() {
        return plate;
    }

    public String getInfractionId() {
        return infractionId;
    }
    @Override
    public int hashCode() {
        return Objects.hash(county, date, plate, infractionId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CountyPlateInfractionAndDate that)) return false;
        return county.equals(that.county) && date.equals(that.date) && plate.equals(that.plate) && infractionId.equals(that.infractionId);
    }
}
