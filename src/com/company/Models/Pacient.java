package com.company.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pacient extends User{
    private String first_name;
    private String last_name;
    private Date dob;
    private Gender gender;

    // Important for functionality
    protected ArrayList<Appointment> appointments = new ArrayList<>(); // TODO: sort by date, everything regarding it
    protected ArrayList<Affection> affections = new ArrayList<>(); // TODO: sort by date, everything regarding it

    public Pacient(String username, String email, String password, String first_name, String last_name, Date dob, Gender gender) {
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

    public Date getDob() {
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

    public void setDob(Date dob) {
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
}
