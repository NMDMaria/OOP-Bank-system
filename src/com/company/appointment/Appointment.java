package com.company.appointment;

import com.company.app.App;
import com.company.procedure.medicalprocedure.MedicalProcedure;
import com.company.user.patient.Patient;

import java.time.*;
import java.util.Objects;

public class Appointment {
    private Integer id;
    private Integer doctor = null;
    private Integer patient = null;
    private LocalDateTime date;
    private Status status;
    private String specialization;
    private MedicalProcedure medicalProcedure;

    public Appointment()
    {
        id = null;
        patient = null;
        date = null;
        status = null;
        specialization = null;
        medicalProcedure = null;
    }

    public Appointment(Integer id, Integer patient,
                       LocalDateTime date, Status status, String specialization, MedicalProcedure medicalProcedure) {
        this.id = id;
        this.patient = patient;
        this.date = date;
        this.status = status;
        this.specialization = specialization;
        this.medicalProcedure = medicalProcedure;
    }

    public Appointment(Integer id, Integer patient,
                       LocalDate date, Integer hour, Integer minute, Status status, String specialization, MedicalProcedure medicalProcedure) {
        this.id = id;
        this.patient = patient;
        this.date = LocalDateTime.of(date, LocalTime.of(hour, minute));
        this.status = status;
        this.specialization = specialization;
        this.medicalProcedure = medicalProcedure;
    }

    public Appointment(Integer id, Integer doctor, Integer patient,
                       LocalDateTime date, Status status, String specialization, MedicalProcedure medicalProcedure) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.status = status;
        this.specialization = specialization;
        this.medicalProcedure = medicalProcedure;
    }

    public Appointment(Integer id, Integer patient,
                       Integer day, Integer month, Integer year,Integer hour, Integer minute, Status status, String specialization, MedicalProcedure medicalProcedure) {
        this(id,patient, LocalDateTime.of(year, month, day, hour, minute), status, specialization, medicalProcedure);
    }

    public Integer getDoctor() {
        return doctor;
    }

    public Integer getPatient() {
        return patient;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDoctor(Integer doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Integer patient) {
        this.patient = patient;
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

    public Integer getId() {
        return id;
    }
}
