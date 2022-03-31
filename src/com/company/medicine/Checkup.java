package com.company.medicine;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Checkup extends MedicalProcedure {
    protected Affection diagnosis;
    protected List<Treatment> treatments = new ArrayList<>();
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

    public List<Treatment> getTreatments() {
        return new ArrayList<>(treatments);
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments.addAll(treatments);
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        String result = "\t\tCheckup\n";
        if (diagnosis != null) {
            result += "Diagnosis was: " + diagnosis;
            if (!treatments.isEmpty())
            {
                result += "\nTreatment list: ";
                for (int i = 1; i <= treatments.size(); i++) {
                    result = result + "\n" + i + treatments.get(i - 1);
                }
            }
            else result = result + "\nWith no treatments.";
        }
        result += "\nMotive: " + motive;
        result += "\nObservations: " + observations;
        return result;
    }
}
