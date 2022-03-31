package com.company.medicine;

import java.util.Comparator;

public class DateComparator implements Comparator<Appointment>{
    @Override
    public int compare(Appointment a, Appointment b) {
        return a.date.compareTo(b.date);
    }
}
