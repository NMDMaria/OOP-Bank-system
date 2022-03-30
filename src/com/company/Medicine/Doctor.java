package com.company.Medicine;

import com.company.Administrative.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Doctor extends User {
    protected String first_name;
    protected String last_name;
    protected LocalDate dob;
    protected LocalDate doe; // date of employment
    protected String phone_number;
    protected Double salary;
    protected String job_name;
    protected String specialization;

    protected ArrayList<Appointment> appointments = new ArrayList<>();

    public Doctor(String username, String email, String password, String first_name, String last_name, LocalDate dob, LocalDate doe, String phone_number, Double salary, String job_name, String specialization) {
        super(username, email, password);
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.doe = doe;
        this.phone_number = phone_number;
        this.salary = salary;
        this.job_name = job_name;
        this.specialization = specialization;
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

    public LocalDate getDoemployment() {
        return doe;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Double getSalary() {
        return salary;
    }

    public String getJob_name() {
        return job_name;
    }

    public String getSpecialization() {
        return specialization;
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

    public void setDoe(LocalDate doe) {
        this.doe = doe;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", dob=" + dob +
                ", doe=" + doe +
                ", phone_number='" + phone_number + '\'' +
                ", salary=" + salary +
                ", job_name='" + job_name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", appointments=" + appointments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(first_name, doctor.first_name) && Objects.equals(last_name, doctor.last_name) && Objects.equals(dob, doctor.dob) && Objects.equals(doe, doctor.doe) && Objects.equals(phone_number, doctor.phone_number) && Objects.equals(salary, doctor.salary) && Objects.equals(job_name, doctor.job_name) && Objects.equals(specialization, doctor.specialization) && Objects.equals(appointments, doctor.appointments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, last_name, dob, doe, phone_number, salary, job_name, specialization, appointments);
    }


}
