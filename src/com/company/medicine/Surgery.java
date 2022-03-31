package com.company.medicine;

import java.time.LocalTime;

public class Surgery extends MedicalProcedure {
    protected String type;
    protected Severity risk;
    protected String complications;

    public Surgery(LocalTime start_time, String type) {
        super(start_time);
        this.type = type;
    }

    public Surgery(Integer start_hour, Integer start_minute, String type) {
        super(start_hour, start_minute);
        this.type = type;
    }

    public Surgery(Integer start_hour, Integer start_minute, String type, Severity risk) {
        super(start_hour, start_minute);
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
