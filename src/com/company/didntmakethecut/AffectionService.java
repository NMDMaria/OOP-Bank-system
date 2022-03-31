package com.company.didntmakethecut;

import com.company.medicine.Affection;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AffectionService{
    Affection affection;

    public void markCured(LocalDate end_date)
    {
        if (end_date.isAfter(this.affection.getStartDate())) {
            this.affection.setCured(true);
            this.affection.setEndDate(end_date);
        }
    }

    public long duration()
    {
        if (this.affection.getEndDate() == null)
            return Long.MAX_VALUE;
        return this.affection.getStartDate().until(this.affection.getEndDate(), ChronoUnit.DAYS);
    }
}
