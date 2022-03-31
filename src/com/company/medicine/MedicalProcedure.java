package com.company.medicine;

import java.time.*;

public class MedicalProcedure {
    protected LocalTime start_time;
    protected LocalTime duration;

    public MedicalProcedure(LocalTime start_time) {
        this.start_time = start_time;
        this.duration = LocalTime.of(0, 0); // wasn't made
    }

    public MedicalProcedure(Integer start_hour, Integer start_minute) {
        this.start_time = LocalTime.of(start_hour, start_minute);
        this.duration = LocalTime.of(0, 0);
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setStartTime(LocalTime start_time) {
        this.start_time = start_time;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Medical procedure with start at " + start_time + " and duration " + duration;
    }
}
