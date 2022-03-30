package com.company.didntmakethecut;

import com.company.Medicine.Affection;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AffectionService{
    Affection affection;

    public void markCured(LocalDate end_date)
    {
        if (end_date.isAfter(this.affection.getStart_date())) {
            this.affection.setCured(true);
            this.affection.setEnd_date(end_date);
        }
    }

    public long duration()
    {
        if (this.affection.getEnd_date() == null)
            return Long.MAX_VALUE;
        return this.affection.getStart_date().until(this.affection.getEnd_date(), ChronoUnit.DAYS);
    }
}
