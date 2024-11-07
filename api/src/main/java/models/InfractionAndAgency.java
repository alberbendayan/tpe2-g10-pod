package models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class InfractionAndAgency implements DataSerializable {
    private String infractionId;
    private String agency;
    private String infractionName;

    public InfractionAndAgency(){}
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

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(infractionId);
        objectDataOutput.writeUTF(agency);
        objectDataOutput.writeUTF(infractionName);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        infractionId = objectDataInput.readUTF();
        agency = objectDataInput.readUTF();
        infractionName = objectDataInput.readUTF();
    }
}
