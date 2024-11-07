package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String plate;
    private String infractionId;
    private Long fineAmount;
    private String issuingAgency;
    private LocalDate issueDate;
    private String countyName;

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
}
