package com.company.Models;

import java.util.Date;

public class Healthcare_employee extends User{
    private String first_name;
    private String last_name;
    private Date dob;
    private Date doe; // date of employment
    private String phone_number;
    private Double salary;
    private String job_name;
    private String specialization;

    // Important for functionality
    private Medical_procedure[] medical_procedures; //TODO: sort them based on date

    public Healthcare_employee(String username, String email, String password, String first_name, String last_name, Date dob, Date doe, String phone_number, Double salary, String job_name, String specialization) {
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

    public Date getDob() {
        return dob;
    }

    public Date getDoemployment() {
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

    public Medical_procedure[] getMedical_procedures() {
        return medical_procedures;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setDoe(Date doe) {
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

    public void setMedical_procedures(Medical_procedure[] medical_procedures) {
        this.medical_procedures = medical_procedures;
    }
}
