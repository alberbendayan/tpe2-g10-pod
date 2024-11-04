package models;

import java.io.Serializable;
import java.util.Objects;

public class InfractionAndAmount implements Serializable {
    private static final Long serialVersionUID = 1L;
    private String infractionId;
    private Long amount;
    private String infractionName;

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

}
