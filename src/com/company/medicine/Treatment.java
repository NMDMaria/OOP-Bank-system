package com.company.medicine;

public class Treatment {
    protected String drug;
    protected Integer numberOfDays;
    protected Float units; // units per day of treatment

    public Treatment(String drug , Integer number_days, Float units) {
        this.drug = drug;
        this.numberOfDays = number_days;
        this.units = units;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Float getUnits() {
        return units;
    }

    public void setUnits(Float units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "Treatment with " +  drug + " for " + numberOfDays + " days with " + units + " units per day.";
    }
}
