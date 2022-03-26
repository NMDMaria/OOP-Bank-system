package com.company.Models;

import java.util.Date;

public class Pacient {
    public enum Sex{
        FEMALE,
        MALE
    }

    private Integer id;
    private String first_name;
    private String last_name;
    private Date dob;
    private Sex sex;

    public Pacient(Integer id, String first_name, String last_name, Date dob, Sex sex) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.sex = sex;
    }

    public Integer getId() {
        return id;
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

    public Sex getSex() {
        return sex;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
