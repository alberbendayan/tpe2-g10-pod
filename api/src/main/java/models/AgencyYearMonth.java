package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class AgencyYearMonth implements Serializable {
    private static final long serialVersionUID = 1L;
    private String agency;
    private int year;
    private int month;

    private UUID ticketID;

    public AgencyYearMonth(String agency, LocalDate date, UUID ticketID) {
        this.agency = agency;
        this.year = date.getYear();
        this.month = date.getMonthValue();
        this.ticketID=ticketID;
    }

    public String getAgency() {
        return agency;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
    public UUID getTicketID() {
        return ticketID;
    }

    @Override
    public String toString() {
        return agency + ";" + year + ";" + month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgencyYearMonth that)) return false;
        return year == that.year && month == that.month && agency.equals(that.agency) && ticketID.equals(that.ticketID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agency, year, month,ticketID);
    }
}
