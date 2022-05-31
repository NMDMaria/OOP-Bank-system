package com.company.procedure.checkup;

import com.company.procedure.treatment.Treatment;
import com.company.procedure.affliction.Affliction;
import com.company.procedure.medicalprocedure.MedicalProcedure;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Checkup extends MedicalProcedure {
    private Affliction diagnosis;
    private List<Treatment> treatments = new ArrayList<>();
    private String motive;
    private String observations;

    public Checkup()
    {
        super();
        diagnosis = null;
        motive = null;
        observations = null;
    }

    public Checkup(Integer id, Integer appointmentId, LocalTime startTime, LocalTime duration, String motive, String observations) {
        super(id, appointmentId, startTime, duration);
        this.motive = motive;
        this.observations = observations;
    }

    public Checkup(Integer id, LocalTime startTime, String motive) {
        super(id, startTime);
        this.motive = motive;
    }

    public Affliction getDiagnosis() {
        return diagnosis;
    }

    public String getMotive() {
        return motive;
    }

    public String getObservations() {
        return observations;
    }

    public void setDiagnosis(Affliction diagnosis) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Checkup checkup = (Checkup) o;
        return Objects.equals(motive, checkup.motive) && Objects.equals(observations, checkup.observations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), motive, observations);
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
                    result = result + "\n" + i + ") "+ treatments.get(i - 1);
                }
            }
            else result = result + "\nWith no treatments.";
        }
        result += "\nMotive: " + motive;
        result += "\nObservations: " + observations;
        return result;
    }
}
