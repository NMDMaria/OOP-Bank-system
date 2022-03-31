package com.company.medicine;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PatientService {
    private static PatientService instance;
    private PatientService() {}

    public static PatientService getInstance()
    {
        if (instance == null)
            instance = new PatientService();
        return instance;
    }

    public Integer findAppointment(Patient patient, LocalDateTime date)
    {
        List<Appointment> appointments = patient.getAppointments();
        for (Appointment appointment: appointments) {
            if (appointment.date.getYear() == date.getYear() &&
                appointment.date.getMonth() == date.getMonth() &&
                appointment.date.getDayOfMonth() == date.getDayOfMonth() &&
                appointment.date.getHour() == date.getHour() &&
                appointment.date.getMinute() == date.getMinute()){
                return appointments.indexOf(appointment);
            }
        }
        return -1;
    }

    public Appointment makeAppointment(Patient patient, Scanner sc)
    {
        try {
            System.out.println("----------------------------");
            MedicalProcedure aux;
            System.out.printf("Date of appointment (DD/MM/YYYY): ");
            String[] aux_dob = sc.nextLine().split("/");
            if (aux_dob.length != 3)
                throw new Exception("Date invalid.");
            System.out.println("Time (first hours, then minutes): ");
            Integer hours;
            Integer minutes;
            hours = Integer.parseInt(sc.nextLine());
            minutes = Integer.parseInt(sc.nextLine());

            LocalDateTime date = LocalDateTime.of(Integer.parseInt(aux_dob[2]), Integer.parseInt(aux_dob[1]), Integer.parseInt(aux_dob[0]), hours, minutes);
            System.out.println("Specialization");
            String specialization = sc.nextLine();
            System.out.println("Type of appointment (checkup/surgery): ");
            String type = sc.nextLine();

            switch (type) {
                case "checkup": {
                    System.out.println("Motive for checkup: ");
                    String motive = sc.nextLine();
                    aux = new Checkup(LocalTime.of(date.getHour(), date.getMinute()), motive);
                    break;
                }
                case "surgery": {
                    System.out.println("Type of surgery: ");
                    String surgery_type = sc.nextLine();
                    aux = new Surgery(date.getHour(), date.getMinute(), surgery_type);
                    break;
                }
                default:
                    throw new Exception("Invalid type");
            }
            Appointment app = new Appointment(date, Status.WAITING, specialization, aux);
            patient.appointments.add(app);
            return app;
        }
        catch (NumberFormatException ne)
        {
            System.out.println("Invalid number/date.");
            return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void cancelAppointment(Patient patient, LocalDateTime date) throws Exception {
        int index = this.findAppointment(patient, date);
        if (index != -1)
        {
            patient.appointments.get(index).setStatus(Status.CANCELLED);
        }
        else throw new Exception("No appointment at given date.");
    }

    public void moveAppointment(Patient patient, LocalDateTime date, LocalDateTime newDate) throws Exception
    {
        int index = this.findAppointment(patient, date);
        if (index != -1)
        {
            int checkIndex = this.findAppointment(patient, newDate);
            if (checkIndex == -1 || patient.appointments.get(index).getStatus() == Status.CANCELLED) {
                patient.appointments.get(index).setDate(newDate);
                System.out.println("Appointment moved");
            }
            else throw new Exception("There's an ongoing appointment on this date.");
        }
        else throw new Exception("No appointment at the given date.");
    }

    public void viewAppointments(Patient patient, Integer year, Integer month, Integer day)
    {
        System.out.println("----------------------------");
        List<Appointment> appointments = patient.getAppointments();
        DateComparator dateComparator = new DateComparator();
        Collections.sort(appointments, dateComparator);
        for (Appointment appointment:appointments) {
            if (appointment.date.getYear() == year &&
                appointment.date.getMonthValue() == month &&
                appointment.date.getDayOfMonth() == day){
                System.out.println(appointment);
            }
        }
        System.out.println("----------------------------");
    }

    public void viewAppointments(Patient patient, Integer year, Integer month)
    {
        System.out.println("----------------------------");
        System.out.printf("Appointments for %d-%d\n", month, year);
        List<Appointment> appointments = patient.getAppointments();
        DateComparator dateComparator = new DateComparator();
        Collections.sort(appointments, dateComparator);
        for (Appointment appointment:appointments) {
            if (appointment.date.getYear() == year &&
                    appointment.date.getMonthValue() == month){
                System.out.println(appointment);
            }
        }
        System.out.println("----------------------------");

    }

    public void viewAppointments(Patient patient, Integer year)
    {
        System.out.println("----------------------------");
        System.out.printf("Appointments for %d\n", year);
        List<Appointment> appointments = patient.getAppointments();
        DateComparator dateComparator = new DateComparator();
        Collections.sort(appointments, dateComparator);
        for (Appointment appointment:appointments) {
            if (appointment.date.getYear() == year){
                System.out.println(appointment);
            }
        }
        System.out.println("----------------------------");

    }
}
