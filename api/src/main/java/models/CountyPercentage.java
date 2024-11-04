package models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CountyPercentage {
    private String county;
    private Double percentage;

    public CountyPercentage(String county, Double percentage) {
        this.county = county;
        this.percentage =  truncateToTwoDecimals(percentage);
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCounty() {
        return county;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = truncateToTwoDecimals(percentage);
    }

    private Double truncateToTwoDecimals(Double value) {
        if (value == null) {
            return null;
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.DOWN);
        return bd.doubleValue();
    }

    @Override
    public String toString() {
        return county+";"+percentage;
    }

}
