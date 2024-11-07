package models;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Ticket implements DataSerializable {
    private UUID id;
    private String plate;
    private String infractionId;
    private Long fineAmount;
    private String issuingAgency;
    private LocalDate issueDate;
    private String countyName;

    public Ticket() {
    }
    public Ticket(String plate, String infractionId, Long fineAmount, String issuingAgency, LocalDate issueDate, String countyName) {
        this.id=UUID.randomUUID();
        this.plate = plate;
        this.infractionId = infractionId;
        this.fineAmount = fineAmount;
        this.issuingAgency = issuingAgency;
        this.issueDate = issueDate;
        this.countyName = countyName;
    }

    // Getters and Setters
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getInfractionId() {
        return infractionId;
    }

    public UUID getId() {
        return id;
    }
    public void setInfractionId(String infractionId) {
        this.infractionId = infractionId;
    }

    public Long getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Long fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getIssuingAgency() {
        return issuingAgency;
    }

    public void setIssuingAgency(String issuingAgency) {
        this.issuingAgency = issuingAgency;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(plate);
        objectDataOutput.writeUTF(infractionId);
        objectDataOutput.writeLong(fineAmount);
        objectDataOutput.writeUTF(issuingAgency);
        objectDataOutput.writeUTF(issueDate.toString());
        objectDataOutput.writeUTF(countyName);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        plate = objectDataInput.readUTF();
        infractionId = objectDataInput.readUTF();
        fineAmount = objectDataInput.readLong();
        issuingAgency = objectDataInput.readUTF();
        issueDate = LocalDate.parse(objectDataInput.readUTF());
        countyName = objectDataInput.readUTF();
    }
}
