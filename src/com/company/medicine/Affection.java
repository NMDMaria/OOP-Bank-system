package com.company.medicine;

import java.time.LocalDate;

public class Affection {
    protected String name;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected Severity severity;
    protected Boolean cured;

    public Affection(String name, LocalDate startDate, LocalDate endDate, Severity severity, Boolean cured) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.severity = severity;
        this.cured = cured;
    }

    public Affection(String name, LocalDate startDate, Severity severity) {
        this.name = name;
        this.startDate = startDate;
        this.severity = severity;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Boolean getCured() {
        return cured;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public void setCured(Boolean cured) {
        this.cured = cured;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Affection name: '" + name + '\n' +
                ", start date " + startDate +
                ", end date " + endDate +
                ", severity " + severity;
    }
}
