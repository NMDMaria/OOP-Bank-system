package com.company.procedure.medicalprocedure;

import java.time.*;
import java.util.Objects;

public class MedicalProcedure {
    private Integer id;
    private Integer appointmentId;
    private LocalTime startTime;
    private LocalTime duration;

    public MedicalProcedure()
    {
        id = null;
        appointmentId = null;
        startTime = null;
        duration = null;
    }

    public MedicalProcedure(Integer id, LocalTime startTime) {
        this.id = id;
        this.startTime = startTime;
        this.duration = LocalTime.of(0, 0); // wasn't made
    }

    public MedicalProcedure(Integer id, Integer startHour, Integer startMinute) {
        this.id = id;
        this.startTime = LocalTime.of(startHour, startMinute);
        this.duration = LocalTime.of(0, 0);
    }

    public MedicalProcedure(Integer id, Integer appointmentId, LocalTime startTime, LocalTime duration) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public Integer getAppointmentId() {
        return appointmentId;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (getClass() != o.getClass() && !(o instanceof MedicalProcedure))) {
            return false;
        }
        MedicalProcedure procedure = (MedicalProcedure) o;
        return Objects.equals(id, procedure.id) && Objects.equals(appointmentId, procedure.appointmentId) && Objects.equals(startTime, procedure.startTime) && Objects.equals(duration, procedure.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appointmentId, startTime, duration);
    }

    @Override
    public String toString() {
        return "Medical procedure with start at " + startTime + " and duration " + duration;
    }
}
