package com.company.Medicine;

import java.time.LocalDate;

public class Affection {
    protected String name;
    protected LocalDate start_date;
    protected LocalDate end_date;
    protected Severity severity;
    protected Boolean cured;

    public Affection(String name, LocalDate start_date, LocalDate end_date, Severity severity, Boolean cured) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.severity = severity;
        this.cured = cured;
    }

    public Affection(String name, LocalDate start_date, Severity severity) {
        this.name = name;
        this.start_date = start_date;
        this.severity = severity;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStart_date() {
        return start_date;
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

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public void setCured(Boolean cured) {
        this.cured = cured;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }


    @Override
    public String toString() {
        return "Affection{" +
                "name='" + name + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", severity=" + severity +
                ", cured=" + cured +
                '}';
    }
}
