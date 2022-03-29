package com.company.Models;

import java.time.LocalDateTime;

public class Surgery extends Medical_procedure{
    protected String type;
    protected Risk risk;
    protected String complications;

    public Surgery(LocalDateTime start_time, String type, Risk risk) {
        super(start_time);
        this.type = type;
        this.risk = risk;
    }

    public Surgery(Integer start_day, Integer start_month, Integer start_year, Integer start_hour, Integer start_minute, String type, Risk risk) {
        super(start_day, start_month, start_year, start_hour, start_minute);
        this.type = type;
        this.risk = risk;
    }

    public String getType() {
        return type;
    }

    public Risk getRisk() {
        return risk;
    }

    public String getComplications() {
        return complications;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public void setComplications(String complications) {
        this.complications = complications;
    }
}
