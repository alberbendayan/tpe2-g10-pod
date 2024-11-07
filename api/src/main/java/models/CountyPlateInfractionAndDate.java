package models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class CountyPlateInfractionAndDate implements DataSerializable {
    private String county;
    private String plate;
    private LocalDate date;
    private String infractionId;
    private UUID ticketId;

    public CountyPlateInfractionAndDate() {
    }
    public CountyPlateInfractionAndDate(String county, LocalDate date, String plate, String infractionId, UUID ticketId) {
        this.county = county;
        this.date = date;
        this.plate = plate;
        this.infractionId = infractionId;
        this.ticketId=ticketId;
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
        return Objects.hash(county, date, plate, infractionId, ticketId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CountyPlateInfractionAndDate that)) return false;
        return county.equals(that.county) && date.equals(that.date) && plate.equals(that.plate) && infractionId.equals(that.infractionId) && ticketId.equals(that.ticketId);
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(county);
        objectDataOutput.writeUTF(plate);
        objectDataOutput.writeUTF(infractionId);
        objectDataOutput.writeUTF(ticketId.toString());
        objectDataOutput.writeUTF(date.toString());
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        county = objectDataInput.readUTF();
        plate = objectDataInput.readUTF();
        infractionId = objectDataInput.readUTF();
        ticketId = UUID.fromString(objectDataInput.readUTF());
        date = LocalDate.parse(objectDataInput.readUTF());
    }
}
