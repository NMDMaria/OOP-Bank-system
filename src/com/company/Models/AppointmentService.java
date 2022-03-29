package com.company.Models;

import java.time.LocalDateTime;

public class AppointmentService {
    Appointment appointment = null;

    public Appointment makeAppointmentCheckup(LocalDateTime date, String specialization, String motive)
    {
        this.appointment = new Appointment(date, Status.WAITING, specialization, new Checkup(date, motive));
        return this.appointment;
    }

    public Appointment makeAppointmentLab_test(LocalDateTime date, String specialization)
    {
        this.appointment = new Appointment(date, Status.WAITING, specialization, new Lab_test(date, null));
        return this.appointment;
    }

    public Appointment makeAppointmentSurgery(LocalDateTime date, String specialization, String type, Risk risk)
    {
        this.appointment = new Appointment(date, Status.WAITING, specialization, new Surgery(date, type, risk));
        return this.appointment;
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
        if (!this.appointment.date.equals(new_date) && this.appointment.date.isBefore(new_date))
        {
            this.appointment.date = new_date;
            this.appointment.medical_procedure.setStart_time(new_date);
        }
    }
}
