package com.company.Medicine;

import com.company.Administrative.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Patient extends User {
    private String first_name;
    private String last_name;
    private LocalDate dob;
    private Gender gender;

    protected ArrayList<Appointment> appointments = new ArrayList<>();
    protected ArrayList<Affection> affections = new ArrayList<>();

    public Patient(String username, String email, String password, String first_name, String last_name, LocalDate dob, Gender gender) {
        super(username, email, password);
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.gender = gender;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public ArrayList<Affection> getAffections() {
        return affections;
    }

    public void setAffections(ArrayList<Affection> affections) {
        this.affections = affections;
    }

    public void sortAppointments()
    {
        this.appointments.sort(Appointment.AppointmentDate);
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", dob=" + dob +
                ", gender=" + gender +
                ", appointments=" + appointments +
                ", affections=" + affections +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(first_name, patient.first_name) && Objects.equals(last_name, patient.last_name) && Objects.equals(dob, patient.dob) && gender == patient.gender && Objects.equals(appointments, patient.appointments) && Objects.equals(affections, patient.affections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, last_name, dob, gender, appointments, affections);
    }
}
