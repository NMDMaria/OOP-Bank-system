package com.company.Models;

import java.time.LocalDateTime;
import java.util.List;

public class Checkup extends Medical_procedure{
    protected Affection diagnosis;
    protected Treatment[] treatments;
    protected String motive;
    protected String observations; // idk about it

    public Checkup(LocalDateTime start_time, String motive) {
        super(start_time);
        this.motive = motive;
    }

    public Checkup(Integer start_day, Integer start_month, Integer start_year, Integer start_hour, Integer start_minute, String motive) {
        super(start_day, start_month, start_year, start_hour, start_minute);
        this.motive = motive;
    }

    public Affection getDiagnosis() {
        return diagnosis;
    }

    public Treatment[] getTreatments() {
        return treatments;
    }

    public String getMotive() {
        return motive;
    }

    public String getObservations() {
        return observations;
    }

    public void setDiagnosis(Affection diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setTreatments(Treatment[] treatments) {
        this.treatments = treatments;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
