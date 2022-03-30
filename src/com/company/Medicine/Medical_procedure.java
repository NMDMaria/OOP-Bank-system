package com.company.Medicine;

import java.time.*;

public class Medical_procedure{
    protected LocalTime start_time;
    protected LocalTime duration;

    public Medical_procedure(LocalTime start_time) {
        this.start_time = start_time;
        this.duration = LocalTime.of(0, 0); // wasnt made
    }

    public Medical_procedure(Integer start_hour, Integer start_minute) {
        this.start_time = LocalTime.of(start_hour, start_minute);
        this.duration = LocalTime.of(0, 0);
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }
}
