package com.company.procedure.affliction;

import com.company.procedure.Severity;

import java.time.LocalDate;

public class Affliction {
    private Integer id;
    private Integer patientId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate = null;
    private Severity severity;
    private Boolean cured = Boolean.FALSE;

    public Affliction()
    {
        id = null;
        patientId = null;
        name = null;
        startDate = null;
        severity = null;
    }

    public Affliction(Integer id, Integer patientId, String name, LocalDate startDate, LocalDate endDate, Severity severity, Boolean cured) {
        this.id = id;
        this.patientId = patientId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.severity = severity;
        this.cured = cured;
    }

    public Affliction(Integer id, Integer patientId, String name, LocalDate startDate, Severity severity) {
        this.id = id;
        this.patientId = patientId;
        this.name = name;
        this.startDate = startDate;
        this.severity = severity;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public Integer getId() {
        return id;
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
