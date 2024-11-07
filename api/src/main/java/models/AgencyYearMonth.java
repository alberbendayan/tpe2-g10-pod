package models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class AgencyYearMonth implements DataSerializable {
    private String agency;
    private int year;
    private int month;

    public AgencyYearMonth(){}
    public AgencyYearMonth(String agency, LocalDate date) {
        this.agency = agency;
        this.year = date.getYear();
        this.month = date.getMonthValue();
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

    @Override
    public String toString() {
        return agency + ";" + year + ";" + month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgencyYearMonth that)) return false;
        return year == that.year && month == that.month && agency.equals(that.agency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agency, year, month);
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(agency);
        objectDataOutput.writeInt(year);
        objectDataOutput.writeInt(month);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        agency = objectDataInput.readUTF();
        year = objectDataInput.readInt();
        month = objectDataInput.readInt();
    }
}
