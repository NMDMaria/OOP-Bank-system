package com.company.user;

import com.company.appointment.Appointment;
import com.company.appointment.Status;
import com.company.procedure.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DoctorService {
    private static DoctorService instance = null;

    private DoctorService(){}

    public static DoctorService getInstance()
    {
        if (instance == null)
        {
            instance = new DoctorService();
        }
        return instance;
    }

    public Doctor readDoctor(String username, String email, String password, Scanner scanner)
    {
        try {
            System.out.print("First name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last name: ");
            String lastName = scanner.nextLine();

            System.out.print("Date of birth (DD/MM/YYYY): ");
            String[] auxDate = scanner.nextLine().split("/");
            if (auxDate.length != 3)
                throw new Exception("Birthdate invalid.");
            LocalDate dob = LocalDate.of(Integer.parseInt(auxDate[2]), Integer.parseInt(auxDate[1]), Integer.parseInt(auxDate[0]));

            System.out.print("Date of employment (DD/MM/YYYY): ");
            auxDate = scanner.nextLine().split("/");
            if (auxDate.length != 3)
                throw new Exception("Birthdate invalid.");
            LocalDate doe = LocalDate.of(Integer.parseInt(auxDate[2]), Integer.parseInt(auxDate[1]), Integer.parseInt(auxDate[0]));

            System.out.print("Phone number: ");
            String phoneNumber = scanner.nextLine();
            if (!phoneNumber.matches("^[0-9]*$"))
                throw new Exception("Phone number invalid.");

            System.out.print("Salary: ");
            Double salary = Double.parseDouble(scanner.nextLine());

            System.out.print("Job title: ");
            String jobName = scanner.nextLine();

            System.out.print("Specialization: ");
            String specialization = scanner.nextLine();

            return new Doctor(username, email, password, firstName, lastName, dob, doe, phoneNumber, salary, jobName, specialization);
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

    public void doctorMenu(Doctor doctor, Scanner scanner, List<Appointment> appointments)
    {
        System.out.println("Welcome dr." + doctor.getLastName() + " " + doctor.getFirstName());

        String answer;
        do {
            System.out.println("1. Select appointments");
            System.out.println("2. Display waiting procedures");
            System.out.println("0. Exit");

            answer = scanner.nextLine();
            switch (answer) {
                case "1": {
                    this.selectAppointment(doctor, appointments);
                    break;
                }
                case "2": {
                    this.displayProcedures(doctor);
                    break;
                }
            }

        }while(!answer.equals("0"));
    }

    private Boolean isAvailable(Doctor doctor, LocalDateTime date)
    {
        for (Appointment appointment: doctor.getAppointments()) {
            if (appointment.getDate().getYear() == date.getYear() &&
                appointment.getDate().getMonth() == date.getMonth() &&
                appointment.getDate().getDayOfMonth() == date.getDayOfMonth() &&
                appointment.getDate().getHour() == date.getHour() &&
                appointment.getDate().getMinute() == date.getMinute())
                return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private void selectAppointment(Doctor doctor, List<Appointment> appointments)
    {
        System.out.println("Doctor " + doctor.getLastName() + " select appointments");
        System.out.println("----------------------------");
        System.out.println("All waiting appointments are:");

        for (Appointment appointment: appointments) {
            if (isAvailable(doctor, appointment.getDate()) == Boolean.TRUE && appointment.getStatus() == Status.WAITING)
            {
                System.out.println(appointment.getMedicalProcedure());
                System.out.println("Add appointment? (Y/N)");
                String answer;
                Scanner sc = new Scanner(System.in);
                answer = sc.nextLine();

                if (answer.equals("Y") || answer.equals("y"))
                {
                    List<Appointment> auxAppointments = doctor.getAppointments();
                    auxAppointments.add(appointment);
                    doctor.setAppointments(auxAppointments);
                }

                System.out.println("Continue to view? (Y/N)");
                answer = sc.nextLine();

                if (answer.equals("N") || answer.equals("n"))
                {
                    return;
                }
            }
        }

        System.out.println("All waiting appointments were viewed.");
        System.out.println("----------------------------");
    }

    private void displayProcedures(Doctor doctor)
    {
        for (Appointment appointment: doctor.getAppointments()) {
                System.out.println(appointment);
                if (appointment.getStatus() == Status.WAITING) {
                    System.out.println("Proceed?(Y/N)");
                    String answer;
                    Scanner sc = new Scanner(System.in);
                    answer = sc.nextLine();

                    if (answer.equals("Y") || answer.equals("y"))
                    {
                        this.doAppointment(doctor, appointment);
                        System.out.println("Continue to view? (Y/N)");
                        answer = sc.nextLine();

                        if (answer.equals("N") || answer.equals("n"))
                        {
                            return;
                        }
                    }
                }
        }
    }

    private void doAppointment(Doctor doctor, Appointment appointment)
    {
        try {
            System.out.println("Doctor " + doctor.getLastName() + " proceed with appointment.");
            System.out.println("----------------------------");
            System.out.println("Starting appointment.");
            Scanner sc = new Scanner(System.in);
            System.out.println(appointment.getMedicalProcedure());
            System.out.println("Start time (first hours, then minutes, leave empty for current time): ");
            String aux = sc.nextLine();
            int hours;
            int minutes;
            if (aux.equals("\n")) {
                hours = LocalTime.now().getHour();
                minutes = LocalTime.now().getMinute();
            } else {
                hours = Integer.parseInt(aux);
                minutes = Integer.parseInt(sc.nextLine());
            }
            MedicalProcedure medicalProcedure = appointment.getMedicalProcedure();
            medicalProcedure.setStartTime(LocalTime.of(hours, minutes));


            if (medicalProcedure instanceof Checkup) {
                handleCheckup(appointment, medicalProcedure, sc);
            } else if (medicalProcedure instanceof Surgery) {
               handleSurgery(medicalProcedure, sc);
            }
            appointment.setMedicalProcedure(medicalProcedure);
            appointment.setStatus(Status.DONE);
            System.out.println("Appointment done.");
            System.out.println("----------------------------");
        }
        catch (NumberFormatException ne)
        {
            System.out.println("Invalid number/date.");
        }
    }

    private void handleCheckup(Appointment appointment, MedicalProcedure medicalProcedure, Scanner sc)
    {
        System.out.println("Diagnose? (Y/N)");
        String answer = sc.nextLine();
        if (answer.equals("Y") || answer.equals("y")) {
            Affliction diagnose = this.diagnose(appointment.getDate());
            ((Checkup) medicalProcedure).setDiagnosis(diagnose);
            System.out.println("Add treatments? (Y/N)");
            answer = sc.nextLine();
            if (answer.equals("Y") || answer.equals("y")) {
                ((Checkup) medicalProcedure).setTreatments(
                        this.planTreatment(((Checkup) medicalProcedure).getDiagnosis()));
            }
        }
        System.out.println("Observations: ");
        answer = sc.nextLine();
        ((Checkup) medicalProcedure).setObservations(answer);
        System.out.println("Checkup duration (first hours, then minutes, leave empty for default): ");
        String aux = sc.nextLine();
        int hours, minutes;
        if (aux.equals("\n")) {
            hours = 0;
            minutes = 20;
        } else {
            hours = Integer.parseInt(aux);
            minutes = Integer.parseInt(sc.nextLine());
        }

        medicalProcedure.setDuration(LocalTime.of(hours, minutes));
    }

    private void handleSurgery(MedicalProcedure medicalProcedure, Scanner sc)
    {
        System.out.println("Surgery risk: ");
        String answer = sc.nextLine();
        Severity risk;
        switch (answer) {
            case "low":
                risk = Severity.LOW;
                break;
            case "medium":
                risk = Severity.MEDIUM;
                break;
            case "high":
                risk = Severity.HIGH;
                break;
            default:
                risk = Severity.INSIGNIFICANT;
                break;
        }
        ((Surgery) medicalProcedure).setRisk(risk);
        System.out.println("Complications: ");
        answer = sc.nextLine();
        ((Surgery) medicalProcedure).setComplications(answer);
        System.out.println("Duration (first hours, then minutes): ");
        int hours = Integer.parseInt(sc.nextLine());
        int minutes = Integer.parseInt(sc.nextLine());
        medicalProcedure.setDuration(LocalTime.of(hours, minutes));
    }

    private Affliction diagnose(LocalDateTime startDate)
    {
        System.out.println("----------------------------");
        String name, auxSeverity;
        Scanner sc = new Scanner(System.in);

        System.out.println("What's your diagnose?");
        System.out.print("Name of affection: ");
        name = sc.nextLine();

        System.out.print("Severity (low/medium/high/insignificant): ");
        auxSeverity = sc.nextLine();

        Severity severity;
        switch (auxSeverity)
        {
            case "low": severity = Severity.LOW; break;
            case "medium": severity = Severity.MEDIUM; break;
            case "high": severity = Severity.HIGH; break;
            default:
                severity = Severity.INSIGNIFICANT; break;
        }

        return new Affliction(name, LocalDate.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth()), severity);
    }

    private List<Treatment> planTreatment(Affliction diagnosis)
    {
        try {
            System.out.println("Treatment for " + diagnosis.getName());
            System.out.println("----------------------------");
            List<Treatment> treatments = new ArrayList<>();
            String answer;
            Scanner sc = new Scanner(System.in);
            do {
                String drug;
                System.out.print("Drug name: ");
                drug = sc.nextLine();
                int numberOfDays;
                System.out.print("Number of days for treatment: ");
                numberOfDays = Integer.parseInt(sc.nextLine());
                System.out.print("Units per day: ");
                float units;
                units = Float.parseFloat(sc.nextLine());

                Treatment treatment = new Treatment(drug, numberOfDays, units);
                treatments.add(treatment);
                System.out.println("Add treatment (Y/N)");
                answer = sc.nextLine();
            } while (answer.equals("Y") || answer.equals("y"));

            return treatments;
        }
        catch (NumberFormatException ne)
        {
            System.out.println("Invalid number.");
            return null;
        }
    }

}
