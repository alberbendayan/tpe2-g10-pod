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
        this.id = UUID.randomUUID();
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
    public String toString() {
        return "Ticket{" +
                "plate='" + plate + '\'' +
                ", infractionId='" + infractionId + '\'' +
                ", fineAmount=" + fineAmount +
                ", issuingAgency='" + issuingAgency + '\'' +
                ", issueDate=" + issueDate +
                ", countyName='" + countyName + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (plate != null ? !plate.equals(ticket.plate) : ticket.plate != null) return false;
        if (infractionId != null ? !infractionId.equals(ticket.infractionId) : ticket.infractionId != null)
            return false;
        if (fineAmount != null ? !fineAmount.equals(ticket.fineAmount) : ticket.fineAmount != null) return false;
        if (issuingAgency != null ? !issuingAgency.equals(ticket.issuingAgency) : ticket.issuingAgency != null)
            return false;
        if (issueDate != null ? !issueDate.equals(ticket.issueDate) : ticket.issueDate != null) return false;
        return countyName != null ? countyName.equals(ticket.countyName) : ticket.countyName == null;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeObject(id);
        objectDataOutput.writeUTF(plate);
        objectDataOutput.writeUTF(infractionId);
        objectDataOutput.writeLong(fineAmount);
        objectDataOutput.writeUTF(issuingAgency);
        objectDataOutput.writeUTF(countyName);
        objectDataOutput.writeLong(issueDate.toEpochDay());
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        id = (UUID) objectDataInput.readObject();
        plate = objectDataInput.readUTF();
        infractionId = objectDataInput.readUTF();
        fineAmount = objectDataInput.readLong();
        issuingAgency = objectDataInput.readUTF();
        countyName = objectDataInput.readUTF();
        issueDate = LocalDate.ofEpochDay(objectDataInput.readLong());
    }
}
