package com.company.appointment;

import com.company.appointment.Appointment;

import java.util.Comparator;

public class DateComparator implements Comparator<Appointment>{
    @Override
    public int compare(Appointment a, Appointment b) {
        return a.getDate().compareTo(b.getDate());
    }
}
