package com.company.medicine;

import com.company.administrative.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient extends User {
    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected Gender gender;

    protected List<Appointment> appointments = new ArrayList<>();
    protected List<Affection> affections = new ArrayList<>();

    public Patient(String username, String email, String password, String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
        super(username, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments.clear();
        this.appointments.addAll(appointments);
    }

    public List<Affection> getAffections() {
        return new ArrayList<>(affections);
    }

    public void setAffections(ArrayList<Affection> affections) {
        this.affections.clear();
        this.affections.addAll(affections);
    }


    @Override
    public String toString() {
        return  "User " + username + " with email " + email +
                "\nPatient " + firstName + ' ' + lastName + '\n' +
                "Date of birth: " + dateOfBirth +
                "\n Gender: " + gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(firstName, patient.firstName) && Objects.equals(lastName, patient.lastName) && Objects.equals(dateOfBirth, patient.dateOfBirth) && gender == patient.gender && Objects.equals(appointments, patient.appointments) && Objects.equals(affections, patient.affections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth, gender, appointments, affections);
    }
}
