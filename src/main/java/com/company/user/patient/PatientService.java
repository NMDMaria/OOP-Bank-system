package com.company.user.patient;

import com.company.app.App;
import com.company.appointment.Appointment;
import com.company.appointment.AppointmentService;
import com.company.audit.AuditService;
import com.company.procedure.affliction.Affliction;
import com.company.user.Gender;
import com.company.user.user.UserService;
import com.company.utils.DateComparator;
import com.company.appointment.Status;
import com.company.procedure.checkup.Checkup;
import com.company.procedure.medicalprocedure.MedicalProcedure;
import com.company.procedure.medicalprocedure.MedicalProcedureService;
import com.company.procedure.surgery.Surgery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class PatientService{
    private static PatientService instance = null;

    private PatientService(){}

    public static PatientService getInstance()
    {
        if (instance == null)
        {
            instance = new PatientService();
        }
        return instance;
    }

    public void patientMenu(Patient patient, Scanner scanner) {
        List<Appointment> appointments = App.getInstance().getAppointments();
        List<MedicalProcedure> medicalProcedures = App.getInstance().getMedicalProcedures();
        List<Checkup> checkups = App.getInstance().getCheckups();
        List<Surgery> surgeries = App.getInstance().getSurgeries();

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
                        Appointment appointment = this.makeAppointment(patient, medicalProcedures, checkups, surgeries, scanner);
                        if (appointment != null)
                            appointments.add(appointment);
                        AuditService.getInstance().write("make_appointment");
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "2": {
                    AuditService.getInstance().write("show_appointments");
                    List<String> auxDate;

                    System.out.println("1. Full date (DD/MM/YYYY)");
                    System.out.println("2. Month and year (MM/YYYY)");
                    System.out.println("3. Year (YYYY)");

                    String secondOption = scanner.nextLine();
                    switch (secondOption) {
                        case "1": {
                            try {
                                auxDate = Arrays.asList(scanner.nextLine().split("/"));
                                if (auxDate.size() != 3)
                                    throw new Exception("Date invalid.");

                                this.viewAppointments(patient, Integer.parseInt(auxDate.get(2)),
                                        Integer.parseInt(auxDate.get(1)), Integer.parseInt(auxDate.get(0)));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case "2": {
                            try {
                                auxDate = List.of(scanner.nextLine().split("/"));
                                if (auxDate.size() != 2)
                                    throw new Exception("Date invalid.");
                                this.viewAppointments(patient, Integer.parseInt(auxDate.get(1)), Integer.parseInt(auxDate.get(0)));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case "3": {
                            try {
                                auxDate = List.of(scanner.nextLine().split("/"));
                                if (auxDate.size() != 1)
                                    throw new Exception("Date invalid.");
                                this.viewAppointments(patient, Integer.parseInt(auxDate.get(0)));
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
                        AuditService.getInstance().write("move_appointment");
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

                        this.moveAppointment(patient, oldDate, newDate);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "4":{
                    try {
                        AuditService.getInstance().write("cancel_appointment");
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

                        this.cancelAppointment(patient, date);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            }
        }while(!answer.equals("0"));
    }

    public Patient readPatient(String username, String email, String password, Scanner scanner){
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

            return new Patient(UserService.userKeyGenerator.nextKey(), username, email, password, firstName, lastName, dob, gender);
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

    private Integer findAppointment(Patient patient, LocalDateTime date)
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

    private Appointment makeAppointment(Patient patient, List<MedicalProcedure> medicalProcedures, List<Checkup> checkups, List<Surgery> surgeries, Scanner sc)
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
                    aux = new Checkup(MedicalProcedureService.medicalProcedureKeyGenerator.nextKey(), LocalTime.of(date.getHour(), date.getMinute()), motive);
                    medicalProcedures.add(aux);
                    checkups.add((Checkup) aux);
                    AuditService.getInstance().write("new_checkup");
                    break;
                }
                case "surgery": {
                    System.out.println("Type of surgery: ");
                    String surgeryType = sc.nextLine();
                    aux = new Surgery(MedicalProcedureService.medicalProcedureKeyGenerator.nextKey(), date.getHour(), date.getMinute(), surgeryType);
                    medicalProcedures.add(aux);
                    surgeries.add((Surgery) aux);
                    AuditService.getInstance().write("new_surgery");
                    break;
                }
                default:
                    throw new Exception("Invalid type");
            }
            Appointment app = new Appointment(AppointmentService.appointmentKeyGenerator.nextKey(), patient.getId(), date, Status.WAITING, specialization, aux);
            aux.setAppointmentId(app.getId());
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

    private void cancelAppointment(Patient patient, LocalDateTime date) throws Exception {
        int index = this.findAppointment(patient, date);
        if (index != -1)
        {
            List<Appointment> appointments = patient.getAppointments();
            Appointment appointment = appointments.get(index);
            appointment.setStatus(Status.CANCELLED);
            patient.setAppointments(appointments);
        }
        else throw new Exception("No appointment at given date.");
    }

    private void moveAppointment(Patient patient, LocalDateTime date, LocalDateTime newDate) throws Exception
    {
        int index = this.findAppointment(patient, date);
        if (index != -1)
        {
            int checkIndex = this.findAppointment(patient, newDate);
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

    private void viewAppointments(Patient patient, Integer year, Integer month, Integer day)
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

    private void viewAppointments(Patient patient, Integer year, Integer month)
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

    private void viewAppointments(Patient patient, Integer year)
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

    public void updateAfflictions(List<Patient> patients, List<Affliction> afflictions) throws Exception {
        for (Affliction affliction: afflictions) {
            Patient patient = patients.stream().filter(x -> x.getId() == affliction.getPatientId()).findFirst().orElse(null);
            if (patient == null)
                throw new Exception("Patient doesn't exist");
            else{
                List<Affliction> patientAfflictions = patient.getAffections();
                if (!patientAfflictions.contains(affliction))
                {
                    patientAfflictions.add(affliction);
                    patient.setAfflictions(patientAfflictions);
                }
            }
        }
    }

    public void updateAppointments(List<Patient> patients, List<Appointment> appointments) throws Exception {
        for (Appointment appointment: appointments) {
            Patient patient = patients.stream().filter(x -> x.getId() == appointment.getPatient()).findFirst().orElse(null);
            if (patient == null)
                throw new Exception("Patient doesn't exist");
            else{
                List<Appointment> patientAppointments = patient.getAppointments();
                if (!patientAppointments.contains(appointment))
                {
                    patientAppointments.add(appointment);
                    patient.setAppointments(patientAppointments);
                }
            }
        }
    }
}
