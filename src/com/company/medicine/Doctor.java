package com.company.medicine;

import com.company.administrative.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Doctor extends User {
    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected LocalDate dateOfEmployment;
    protected String phoneNumber;
    protected Double salary;
    protected String jobName;
    protected String specialization;

    protected List<Appointment> appointments = new ArrayList<>();

    public Doctor(String username, String email, String password, String firstName, String lastName, LocalDate dateOfBirth, LocalDate dateOfEmployment, String phoneNumber, Double salary, String jobName, String specialization) {
        super(username, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfEmployment = dateOfEmployment;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.jobName = jobName;
        this.specialization = specialization;
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

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Double getSalary() {
        return salary;
    }

    public String getJobName() {
        return jobName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDob(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDoe(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<Appointment> getAppointments() {
        return  new ArrayList<>(appointments);
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments.clear();
        this.appointments.addAll(appointments);
    }

    @Override
    public String toString() {
        return "User " + username + " with email " + email +
                "\nDr." + firstName + ' ' + lastName + '\n' +
                "Date of birth: " + dateOfBirth +
                "\nDate of employment: " + dateOfEmployment +
                "\nPhone number: " + phoneNumber +
                "\nSalary: " + salary +
                "\nJob name: " + jobName  +
                "\nSpecialization: " + specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(firstName, doctor.firstName) && Objects.equals(lastName, doctor.lastName) && Objects.equals(dateOfBirth, doctor.dateOfBirth) && Objects.equals(dateOfEmployment, doctor.dateOfEmployment) && Objects.equals(phoneNumber, doctor.phoneNumber) && Objects.equals(salary, doctor.salary) && Objects.equals(jobName, doctor.jobName) && Objects.equals(specialization, doctor.specialization) && Objects.equals(appointments, doctor.appointments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dateOfBirth, dateOfEmployment, phoneNumber, salary, jobName, specialization, appointments);
    }


}
