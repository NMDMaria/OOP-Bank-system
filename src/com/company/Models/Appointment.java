package com.company.Models;

import java.time.LocalDateTime;

public class Appointment {
    protected Healthcare_employee doctor;
    protected Pacient pacient;
    protected LocalDateTime date;
    protected Status status;
    protected String specialization;
    protected Medical_procedure medical_procedure;

    public Appointment(Healthcare_employee doctor, Pacient pacient, LocalDateTime date, Status status, String specialization) {
        this.doctor = doctor;
        this.pacient = pacient;
        this.date = date;
        this.status = status;
        this.specialization = specialization;
    }

    public Healthcare_employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Healthcare_employee doctor) {
        this.doctor = doctor;
    }

    public Pacient getPacient() {
        return pacient;
    }

    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
