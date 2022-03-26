package com.company.Models;

import java.util.Date;
import java.util.List;

public class Healthcare_employee extends Employee{
    private String specialization;

    public Healthcare_employee(Integer id, String first_name, String last_name, Date dob, Date doe, String email, String phone_number, Double salary, String job_name, String specialization) {
        super(id, first_name, last_name, dob, doe, email, phone_number, salary, job_name);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
