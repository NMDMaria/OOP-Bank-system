package com.company.Medicine;

import java.time.LocalTime;
import java.util.ArrayList;

public class Checkup extends Medical_procedure{
    protected Affection diagnosis;
    protected ArrayList<Treatment> treatments = new ArrayList<>();
    protected String motive;
    protected String observations;

    public Checkup(LocalTime start_time, String motive) {
        super(start_time);
        this.motive = motive;
    }

    public Checkup(Integer start_hour, Integer start_minute, String motive) {
        super(start_hour, start_minute);
        this.motive = motive;
    }

    public Affection getDiagnosis() {
        return diagnosis;
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

    public ArrayList<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(ArrayList<Treatment> treatments) {
        this.treatments = treatments;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "Checkup{" +
                "diagnosis=" + diagnosis +
                ", treatments=" + treatments +
                ", motive='" + motive + '\'' +
                ", observations='" + observations + '\'' +
                '}';
    }
}
