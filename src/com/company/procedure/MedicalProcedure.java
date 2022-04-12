package com.company.procedure;

import java.time.*;

public class MedicalProcedure {
    private LocalTime startTime;
    private LocalTime duration;

    public MedicalProcedure(LocalTime startTime) {
        this.startTime = startTime;
        this.duration = LocalTime.of(0, 0); // wasn't made
    }

    public MedicalProcedure(Integer startHour, Integer startMinute) {
        this.startTime = LocalTime.of(startHour, startMinute);
        this.duration = LocalTime.of(0, 0);
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Medical procedure with start at " + startTime + " and duration " + duration;
    }
}
