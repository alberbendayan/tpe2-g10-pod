package models;

import java.io.Serializable;

public class InfractionAndAmountStats implements Serializable {
    private static final Long serialVersionUID = 1L;
    private String infractionName;
    private Long minAmount;
    private Long maxAmount;

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

}
