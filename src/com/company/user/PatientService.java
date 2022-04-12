package com.company.user;

import com.company.appointment.Appointment;
import com.company.appointment.DateComparator;
import com.company.appointment.Status;
import com.company.procedure.Checkup;
import com.company.procedure.MedicalProcedure;
import com.company.procedure.Surgery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PatientService {
    public static void patientMenu(Patient patient, Scanner scanner, List<Appointment> appointments) {
        System.out.println("Welcome " + patient.getLastName() + " " + patient.getFirstName());

        String answer;
        do {
            System.out.println("1. Make an appointment");
            System.out.println("2. Show appointments by date");
            System.out.println("3. Move appointment");
            System.out.println("4. Cancel appointment");
            System.out.println("0. Exit");

            answer = scanner.nextLine();
            switch (answer) {
                case "1": {
                    try {
                        Appointment appointment = PatientService.makeAppointment(patient, scanner);
                        appointments.add(appointment);
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "2": {
                    String[] auxDate;

                    System.out.println("1. Full date (DD/MM/YYYY)");
                    System.out.println("2. Month and year (MM/YYYY)");
                    System.out.println("3. Year (YYYY)");

                    String secondOption = scanner.nextLine();
                    switch (secondOption) {
                        case "1": {
                            try {
                                auxDate = scanner.nextLine().split("/");
                                if (auxDate.length != 3)
                                    throw new Exception("Date invalid.");

                                PatientService.viewAppointments(patient, Integer.parseInt(auxDate[2]),
                                        Integer.parseInt(auxDate[1]), Integer.parseInt(auxDate[0]));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case "2": {
                            try {
                                auxDate = scanner.nextLine().split("/");
                                if (auxDate.length != 2)
                                    throw new Exception("Date invalid.");
                                PatientService.viewAppointments(patient, Integer.parseInt(auxDate[1]), Integer.parseInt(auxDate[0]));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case "3": {
                            try {
                                auxDate = scanner.nextLine().split("/");
                                if (auxDate.length != 1)
                                    throw new Exception("Date invalid.");
                                PatientService.viewAppointments(patient, Integer.parseInt(auxDate[0]));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                    }

                    break;
                }
                case "3": {
                    try{
                        System.out.println("What's the date for the appointment you want to move?");

                        System.out.print("Date (DD/MM/YYYY): ");
                        String[] auxDate, auxTime;
                        auxDate = scanner.nextLine().split("/");
                        if (auxDate.length != 3)
                            throw new Exception("Date invalid.");

                        System.out.print("Time (HH MM): ");
                        auxTime = scanner.nextLine().split(" ");
                        if (auxTime.length != 2)
                            throw new Exception("Time invalid.");

                        LocalDateTime oldDate = LocalDateTime.of(Integer.parseInt(auxDate[2]), Integer.parseInt(auxDate[1]),
                                Integer.parseInt(auxDate[0]), Integer.parseInt(auxTime[0]), Integer.parseInt(auxTime[1]));

                        System.out.println("What's the NEW date for the appointment you want to move?");

                        System.out.print("Date (DD/MM/YYYY): ");
                        auxDate = scanner.nextLine().split("/");
                        if (auxDate.length != 3)
                            throw new Exception("Date invalid.");

                        System.out.print("Time (HH MM): ");
                        auxTime = scanner.nextLine().split(" ");
                        if (auxTime.length != 2)
                            throw new Exception("Time invalid.");

                        LocalDateTime newDate = LocalDateTime.of(Integer.parseInt(auxDate[2]), Integer.parseInt(auxDate[1]),
                                Integer.parseInt(auxDate[0]), Integer.parseInt(auxTime[0]), Integer.parseInt(auxTime[1]));

                        PatientService.moveAppointment(patient, oldDate, newDate);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "4":{
                    try {
                        System.out.println("What's the date for the appointment you want to cancel?");

                        System.out.print("Date (DD/MM/YYYY): ");
                        String[] auxDate, auxTime;
                        auxDate = scanner.nextLine().split("/");
                        if (auxDate.length != 3)
                            throw new Exception("Date invalid.");

                        System.out.print("Time (HH MM): ");
                        auxTime = scanner.nextLine().split(" ");
                        if (auxTime.length != 2)
                            throw new Exception("Time invalid.");

                        LocalDateTime date = LocalDateTime.of(Integer.parseInt(auxDate[2]), Integer.parseInt(auxDate[1]),
                                Integer.parseInt(auxDate[0]), Integer.parseInt(auxTime[0]), Integer.parseInt(auxTime[1]));

                        PatientService.cancelAppointment(patient, date);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            }
        }while(!answer.equals("0"));
    }

    public static Patient readPatient(String username, String email, String password, Scanner scanner){
        try {
            System.out.print("First name: ");
            String firstName = scanner.nextLine();
            System.out.print("Last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Date of birth (DD/MM/YYYY): ");
            String[] auxDate = scanner.nextLine().split("/"); // aux date of birth
            if (auxDate.length != 3)
                throw new Exception("Birthdate invalid.");
            LocalDate dob = LocalDate.of(Integer.parseInt(auxDate[2]), Integer.parseInt(auxDate[1]), Integer.parseInt(auxDate[0])); // date of birth

            System.out.print("Gender (M/F): ");
            String auxGender = scanner.nextLine();
            Gender gender;
            switch (auxGender) {
                case "M":
                case "m":
                    gender = Gender.MALE;
                    break;
                case "F":
                case "f":
                    gender = Gender.FEMALE;
                    break;
                default:
                    throw new Exception("Invalid gender option.");
            }

            return new Patient(username, email, password, firstName, lastName, dob, gender);
        }
        catch (NumberFormatException ne)
        {
            System.out.println("Invalid number/date.");
            return null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static Integer findAppointment(Patient patient, LocalDateTime date)
    {
        List<Appointment> appointments = patient.getAppointments();
        for (Appointment appointment: appointments) {
            LocalDateTime appointmentDate = appointment.getDate();
            if (appointmentDate.getYear() == date.getYear() &&
                appointmentDate.getMonth() == date.getMonth() &&
                appointmentDate.getDayOfMonth() == date.getDayOfMonth() &&
                appointmentDate.getHour() == date.getHour() &&
                appointmentDate.getMinute() == date.getMinute()){
                return appointments.indexOf(appointment);
            }
        }
        return -1;
    }

    private static Appointment makeAppointment(Patient patient, Scanner sc)
    {
        try {
            System.out.println("----------------------------");
            MedicalProcedure aux;
            System.out.print("Date of appointment (DD/MM/YYYY): ");
            String[] auxDate = sc.nextLine().split("/");
            if (auxDate.length != 3)
                throw new Exception("Date invalid.");
            System.out.println("Time (first hours, then minutes): ");
            int hours;
            int minutes;
            hours = Integer.parseInt(sc.nextLine());
            minutes = Integer.parseInt(sc.nextLine());

            LocalDateTime date = LocalDateTime.of(Integer.parseInt(auxDate[2]), Integer.parseInt(auxDate[1]), Integer.parseInt(auxDate[0]), hours, minutes);
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
                    String surgeryType = sc.nextLine();
                    aux = new Surgery(date.getHour(), date.getMinute(), surgeryType);
                    break;
                }
                default:
                    throw new Exception("Invalid type");
            }
            Appointment app = new Appointment(date, Status.WAITING, specialization, aux);
            List<Appointment> appointments = patient.getAppointments();
            appointments.add(app);
            patient.setAppointments(appointments);
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

    private static void cancelAppointment(Patient patient, LocalDateTime date) throws Exception {
        int index = PatientService.findAppointment(patient, date);
        if (index != -1)
        {
            List<Appointment> appointments = patient.getAppointments();
            Appointment appointment = appointments.get(index);
            appointment.setStatus(Status.CANCELLED);
            patient.setAppointments(appointments);
        }
        else throw new Exception("No appointment at given date.");
    }

    private static void moveAppointment(Patient patient, LocalDateTime date, LocalDateTime newDate) throws Exception
    {
        int index = PatientService.findAppointment(patient, date);
        if (index != -1)
        {
            int checkIndex = PatientService.findAppointment(patient, newDate);
            List<Appointment> appointments = patient.getAppointments();
            if (checkIndex == -1 || appointments.get(index).getStatus() == Status.CANCELLED) {
                Appointment appointment = appointments.get(index);
                appointment.setDate(newDate);
                System.out.println("Appointment moved");
                patient.setAppointments(appointments);
            }
            else throw new Exception("There's an ongoing appointment on this date.");
        }
        else throw new Exception("No appointment at the given date.");
    }

    private static void viewAppointments(Patient patient, Integer year, Integer month, Integer day)
    {
        System.out.println("----------------------------");
        List<Appointment> appointments = patient.getAppointments();
        DateComparator dateComparator = new DateComparator();
        Collections.sort(appointments, dateComparator);
        for (Appointment appointment:appointments) {
            LocalDateTime date = appointment.getDate();
            if (date.getYear() == year &&
                date.getMonthValue() == month &&
                date.getDayOfMonth() == day){
                System.out.println(appointment);
            }
        }
        System.out.println("----------------------------");
    }

    private static void viewAppointments(Patient patient, Integer year, Integer month)
    {
        System.out.println("----------------------------");
        System.out.printf("Appointments for %d-%d\n", month, year);
        List<Appointment> appointments = patient.getAppointments();
        DateComparator dateComparator = new DateComparator();
        Collections.sort(appointments, dateComparator);
        for (Appointment appointment:appointments) {
            LocalDateTime date = appointment.getDate();
            if (date.getYear() == year &&
                    date.getMonthValue() == month){
                System.out.println(appointment);
            }
        }
        System.out.println("----------------------------");

    }

    private static void viewAppointments(Patient patient, Integer year)
    {
        System.out.println("----------------------------");
        System.out.printf("Appointments for %d\n", year);
        List<Appointment> appointments = patient.getAppointments();
        DateComparator dateComparator = new DateComparator();
        Collections.sort(appointments, dateComparator);
        for (Appointment appointment:appointments) {
            LocalDateTime date = appointment.getDate();
            if (date.getYear() == year){
                System.out.println(appointment);
            }
        }
        System.out.println("----------------------------");

    }
}
