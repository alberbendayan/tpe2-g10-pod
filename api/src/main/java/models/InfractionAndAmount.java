package models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class InfractionAndAmount implements DataSerializable {

    private String infractionId;
    private Long amount;
    private String infractionName;

    public InfractionAndAmount(){}
    public InfractionAndAmount(String infractionId, Long amount, String infractionName) {
        this.infractionId = infractionId;
        this.amount = amount;
        this.infractionName = infractionName;
    }

    public String getInfractionId() {
        return infractionId;
    }

    public String getInfractionName() {
        return infractionName;
    }

    public Long getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfractionAndAmount that)) return false;
        return infractionId.equals(that.infractionId) && amount.equals(that.amount) && infractionName.equals(that.infractionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infractionId, amount, infractionName);
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(infractionId);
        objectDataOutput.writeLong(amount);
        objectDataOutput.writeUTF(infractionName);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        infractionId = objectDataInput.readUTF();
        amount = objectDataInput.readLong();
        infractionName = objectDataInput.readUTF();
    }
}
