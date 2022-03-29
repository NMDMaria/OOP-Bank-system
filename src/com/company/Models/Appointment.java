package com.company.Models;

import java.time.*;
import java.util.Objects;

public class Appointment {
    protected LocalDateTime date;
    protected Status status;
    protected String specialization;
    protected Medical_procedure medical_procedure;

    public Appointment(LocalDate date, Integer hour, Integer minute, Status status, String specialization, Medical_procedure medical_procedure) {
        this.date = LocalDateTime.of(date, LocalTime.of(hour, minute));
        this.status = status;
        this.specialization = specialization;
        this.medical_procedure = medical_procedure;
    }

    public Appointment(LocalDateTime date, Status status, String specialization, Medical_procedure medical_procedure) {
        this.date = date;
        this.status = status;
        this.specialization = specialization;
        this.medical_procedure = medical_procedure;
    }

    public Appointment(Integer day, Integer month, Integer year,Integer hour, Integer minute, Status status, String specialization, Medical_procedure medical_procedure) {
        this.date = LocalDateTime.of(year, month, day, hour, minute);
        this.status = status;
        this.specialization = specialization;
        this.medical_procedure = medical_procedure;
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

    public Medical_procedure getMedical_procedure() {
        return medical_procedure;
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

    public void setMedical_procedure(Medical_procedure medical_procedure) {
        this.medical_procedure = medical_procedure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(date, that.date) && status == that.status && Objects.equals(specialization, that.specialization) && Objects.equals(medical_procedure, that.medical_procedure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, status, specialization, medical_procedure);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "date=" + date +
                ", status=" + status +
                ", specialization='" + specialization + '\'' +
                ", medical_procedure=" + medical_procedure +
                '}';
    }
}
