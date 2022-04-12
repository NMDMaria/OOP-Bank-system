package com.company.appointment;

import com.company.procedure.MedicalProcedure;

import java.time.*;
import java.util.Objects;

public class Appointment {
    private LocalDateTime date;
    private Status status;
    private String specialization;
    private MedicalProcedure medicalProcedure;

    public Appointment(LocalDate date, Integer hour, Integer minute, Status status, String specialization, MedicalProcedure medicalProcedure) {
        this.date = LocalDateTime.of(date, LocalTime.of(hour, minute));
        this.status = status;
        this.specialization = specialization;
        this.medicalProcedure = medicalProcedure;
    }

    public Appointment(LocalDateTime date, Status status, String specialization, MedicalProcedure medicalProcedure) {
        this.date = date;
        this.status = status;
        this.specialization = specialization;
        this.medicalProcedure = medicalProcedure;
    }

    public Appointment(Integer day, Integer month, Integer year,Integer hour, Integer minute, Status status, String specialization, MedicalProcedure medicalProcedure) {
        this(LocalDateTime.of(year, month, day, hour, minute), status, specialization, medicalProcedure);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    public String getSpecialization() {
        return specialization;
    }

    public MedicalProcedure getMedicalProcedure() {
        return medicalProcedure;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setMedicalProcedure(MedicalProcedure medicalProcedure) {
        this.medicalProcedure = medicalProcedure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(date, that.date) && status == that.status && Objects.equals(specialization, that.specialization) && Objects.equals(medicalProcedure, that.medicalProcedure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, status, specialization, medicalProcedure);
    }

    @Override
    public String toString() {
        String result = "Appointment at " + date +
                " with status " + status;
        result += " specialization " + specialization;
        if (status == Status.DONE)
            result += "\nConcluded with procedure: " + medicalProcedure;
        return result;
    }
}
