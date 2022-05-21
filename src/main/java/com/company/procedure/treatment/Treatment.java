package com.company.procedure.treatment;

public class Treatment {
    private Integer id;
    private Integer medicalProcedure;
    private String drug;
    private Integer numberOfDays;
    private Float units; // units per day of treatment

    public Treatment()
    {
        id = null;
        medicalProcedure = null;
        drug = null;
        numberOfDays = null;
        units = null;
    }

    public Treatment(Integer id, Integer medicalProcedure, String drug , Integer numberDays, Float units) {
        this.id = id;
        this.medicalProcedure = medicalProcedure;
        this.drug = drug;
        this.numberOfDays = numberDays;
        this.units = units;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMedicalProcedure() {
        return medicalProcedure;
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
