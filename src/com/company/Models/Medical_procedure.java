package com.company.Models;

import java.time.*;

public class Medical_procedure{
    protected LocalDateTime start_time;
    protected LocalTime duration; // in minutes

    public Medical_procedure(LocalDateTime start_time) {
        this.start_time = start_time;
        this.duration = LocalTime.of(0, 0);
    }

    public Medical_procedure(Integer start_day, Integer start_month, Integer start_year, Integer start_hour,
                             Integer start_minute) {
        this.start_time = LocalDateTime.of(start_year, start_month, start_day, start_hour, start_minute);
        this.duration = LocalTime.of(0, 0);
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }
}
