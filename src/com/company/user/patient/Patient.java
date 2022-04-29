package com.company.user.patient;

import com.company.procedure.affliction.Affliction;
import com.company.appointment.Appointment;
import com.company.user.Gender;
import com.company.user.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient extends User {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;

    private List<Appointment> appointments = new ArrayList<>();
    private List<Affliction> afflictions = new ArrayList<>();

    public Patient()
    {
        super();
        this.firstName = null;
        this.lastName = null;
        this.dateOfBirth = null;
        this.gender = null;
    }

    public Patient(Integer id, String username, String email, String password, String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
        super(id, username, email, password);
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setAppointments(List<Appointment> appointments) {
        this.appointments.clear();
        this.appointments.addAll(appointments);
    }

    public List<Affliction> getAffections() {
        return new ArrayList<>(afflictions);
    }

    public void setAfflictions(List<Affliction> afflictions) {
        this.afflictions.clear();
        this.afflictions.addAll(afflictions);
    }


    @Override
    public String toString() {
        return  "User " + this.getUsername() + " with email " + this.getEmail() +
                "\nPatient " + firstName + ' ' + lastName + '\n' +
                "Date of birth: " + dateOfBirth +
                "\n Gender: " + gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(firstName, patient.firstName) && Objects.equals(lastName, patient.lastName) && Objects.equals(dateOfBirth, patient.dateOfBirth) && gender == patient.gender && Objects.equals(appointments, patient.appointments) && Objects.equals(afflictions, patient.afflictions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth, gender, appointments, afflictions);
    }
}
