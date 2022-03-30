package com.company.didntmakethecut;

import com.company.Medicine.*;

import java.time.LocalDateTime;

public class AppointmentService {
    Appointment appointment = null;

    public Appointment makeAppointmentCheckup(LocalDateTime date, String specialization, String motive)
    {
        this.appointment = new Appointment(date, Status.WAITING, specialization, new Checkup(date.getHour(), date.getMinute(), motive));
        return this.appointment;
    }

    public Appointment makeAppointmentLab_test(LocalDateTime date, String specialization)
    {
        this.appointment = new Appointment(date, Status.WAITING, specialization, new Lab_test(date.getHour(), date.getMinute(), null));
        return this.appointment;
    }

    public Appointment makeAppointmentSurgery(LocalDateTime date, String specialization, String type, Severity risk)
    {
        return new Appointment(date, Status.CANCELLED, specialization, new Medical_procedure(date.getHour(), date.getMinute()));
    }

    public void cancelAppointment()
    {
        if (this.appointment != null)
        {
            this.appointment.setStatus(Status.CANCELLED);
        }
    }

    public void moveAppointment(LocalDateTime new_date)
    {
        if (!this.appointment.getDate().equals(new_date) && this.appointment.getDate().isBefore(new_date))
        {
            this.appointment.setDate(new_date);
        }
    }
}
