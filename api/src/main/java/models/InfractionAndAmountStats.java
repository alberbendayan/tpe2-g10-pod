package models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.io.Serializable;

public class InfractionAndAmountStats implements DataSerializable {
    private String infractionName;
    private Long minAmount;
    private Long maxAmount;

    public InfractionAndAmountStats() {
    }

    public InfractionAndAmountStats(InfractionAndAmount infractionAndAmount) {
        this.infractionName = infractionAndAmount.getInfractionName();
        this.minAmount = infractionAndAmount.getAmount();
        this.maxAmount = infractionAndAmount.getAmount();
    }

    public InfractionAndAmountStats(String infractionName, Long minAmount, Long maxAmount) {
        this.infractionName = infractionName;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    public String getInfractionName() {
        return infractionName;
    }

    public Long getMinAmount() {
        return minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public Long getDifference(){
        return maxAmount - minAmount;
    }

    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public String toString(){
        return infractionName + ";" + minAmount + ";" + maxAmount + ";" + getDifference();
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(infractionName);
        objectDataOutput.writeLong(minAmount);
        objectDataOutput.writeLong(maxAmount);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        infractionName = objectDataInput.readUTF();
        minAmount = objectDataInput.readLong();
        maxAmount = objectDataInput.readLong();
    }
}
