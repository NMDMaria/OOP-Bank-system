package com.company.procedure;

import java.time.LocalTime;

public class Surgery extends MedicalProcedure {
    private String type;
    private Severity risk;
    private String complications;

    public Surgery(LocalTime startTime, String type) {
        super(startTime);
        this.type = type;
    }

    public Surgery(Integer startHour, Integer startMinute, String type) {
        super(startHour, startMinute);
        this.type = type;
    }

    public Surgery(Integer startHour, Integer startMinute, String type, Severity risk) {
        super(startHour, startMinute);
        this.type = type;
        this.risk = risk;
    }

    public String getType() {
        return type;
    }

    public Severity getRisk() {
        return risk;
    }

    public String getComplications() {
        return complications;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRisk(Severity risk) {
        this.risk = risk;
    }

    public void setComplications(String complications) {
        this.complications = complications;
    }

    @Override
    public String toString() {
        return "\t\tSurgery\n" +
                "type  "+ type +
                "\nrisk " + risk;
    }
}
