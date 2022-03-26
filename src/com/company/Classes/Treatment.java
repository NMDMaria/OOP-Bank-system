package com.company.Classes;

public class Treatment {
    protected String drug;
    protected Integer number_days;
    protected Float units; // units per day of treatment

    public Treatment(String drug , Integer number_days, Float units) {
        this.drug = drug;
        this.number_days = number_days;
        this.units = units;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public Integer getNumber_days() {
        return number_days;
    }

    public void setNumber_days(Integer number_days) {
        this.number_days = number_days;
    }

    public Float getUnits() {
        return units;
    }

    public void setUnits(Float units) {
        this.units = units;
    }
}
