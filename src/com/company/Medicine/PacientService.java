package com.company.Medicine;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

import static java.lang.Math.abs;

public class PacientService {
    private final Patient patient;

    public PacientService(Patient patient)
    {
        this.patient = patient;
    }

    public Integer findAppointment(LocalDateTime date)
    {
        for (Appointment appointment:this.patient.appointments) {
            if (appointment.date.getYear() == date.getYear() &&
                appointment.date.getMonth() == date.getMonth() &&
                appointment.date.getDayOfMonth() == date.getDayOfMonth() &&
                appointment.date.getHour() == date.getHour() &&
                appointment.date.getMinute() == date.getMinute()){
                return this.patient.appointments.indexOf(appointment);
            }
        }
        return -1;
    }

    public void makeAppointment(LocalDateTime date, String specialization, String type)
    {
        Medical_procedure aux;
        Scanner sc = new Scanner(System.in);

        switch (type)
        {
            case "checkup":
            {
                System.out.println("Motive for checkup: ");
                String motive = sc.nextLine();
                aux = new Checkup(LocalTime.of(date.getHour(), date.getMinute()),motive);
                break;
            }
            case "surgery":
            {
                System.out.println("Type of surgery: ");
                String surgery_type = sc.nextLine();
                aux = new Surgery(date.getHour(), date.getMinute(), surgery_type);
                break;
            }
            default:
                return;
        }
        Appointment app = new Appointment(date, Status.WAITING, specialization, aux);
        this.patient.appointments.add(app);
    }

    public void cancelAppointment(LocalDateTime date)
    {
        int index = this.findAppointment(date);
        if (index != -1)
        {
            this.patient.appointments.get(index).setStatus(Status.CANCELLED);
        }
    }

    public void moveAppointment(LocalDateTime date, LocalDateTime new_date)
    {
        int index = this.findAppointment(date);
        if (index != -1)
        {
            int check_index = this.findAppointment(new_date);
            if (check_index == -1)
                this.patient.appointments.get(index).setDate(new_date);
        }
    }

    public void viewAppointments(Integer year, Integer month, Integer day)
    {
        System.out.printf("Appointments for %d-%d-%d\n", day, month, year);
        for (Appointment appointment:this.patient.appointments) {
            if (appointment.date.getYear() == year &&
                appointment.date.getMonthValue() == month &&
                appointment.date.getDayOfMonth() == day){
                System.out.println(appointment.toString());
            }
        }
    }

    public void viewAppointments(Integer year, Integer month)
    {
        System.out.printf("Appointments for %d-%d\n", month, year);
        for (Appointment appointment:this.patient.appointments) {
            if (appointment.date.getYear() == year &&
                    appointment.date.getMonthValue() == month){
                System.out.println(appointment.toString());
            }
        }
    }

    public void viewAppointments(Integer year)
    {
        System.out.printf("Appointments for %d\n", year);
        for (Appointment appointment:this.patient.appointments) {
            if (appointment.date.getYear() == year){
                System.out.println(appointment.toString());
            }
        }
    }
}
