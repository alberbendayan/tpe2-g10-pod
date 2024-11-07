package models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class CountyInfractionAndPlate implements DataSerializable {
    private String county;
    private String plate;
    private String infractionId;

    public CountyInfractionAndPlate() {
    }
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

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(county);
        objectDataOutput.writeUTF(plate);
        objectDataOutput.writeUTF(infractionId);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        county = objectDataInput.readUTF();
        plate = objectDataInput.readUTF();
        infractionId = objectDataInput.readUTF();
    }
}
