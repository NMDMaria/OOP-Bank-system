package com.company.procedure.surgery;

import com.company.procedure.Severity;
import com.company.procedure.medicalprocedure.MedicalProcedure;

import java.time.LocalTime;

public class Surgery extends MedicalProcedure {
    private String type;
    private Severity risk;
    private String complications;

    public Surgery()
    {
        type = null;
        risk = null;
        complications = null;
    }

    public Surgery(Integer id, Integer appointmentId, LocalTime startTime, LocalTime duration, String type, Severity risk, String complications) {
        super(id, appointmentId, startTime, duration);
        this.type = type;
        this.risk = risk;
        this.complications = complications;
    }

    public Surgery(Integer id, LocalTime startTime, String type) {
        super(id, startTime);
        this.type = type;
    }

    public Surgery(Integer id, Integer startHour, Integer startMinute, String type) {
        super(id, startHour, startMinute);
        this.type = type;
    }

    public Surgery(Integer id, Integer startHour, Integer startMinute, String type, Severity risk) {
        super(id, startHour, startMinute);
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
