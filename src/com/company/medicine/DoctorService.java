package com.company.medicine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DoctorService {
    private static DoctorService instance;
    private DoctorService() {}

    public static DoctorService getInstance()
    {
        if (instance == null)
            instance = new DoctorService();
        return instance;
    }

    public void selectAppointment(Doctor doctor, List<Appointment> appointments)
    {
        System.out.println("Doctor " + doctor.getLastName() + " select appointments");
        System.out.println("----------------------------");
        System.out.println("All waiting appointments are:");

        for (Appointment appointment: appointments) {
            if (appointment.getStatus() == Status.WAITING)
            {
                System.out.println(appointment.medicalProcedure);
                System.out.println("Add appointment? (Y/N)");
                String answer;
                Scanner sc = new Scanner(System.in);
                answer = sc.nextLine();

                if (answer.equals("Y") || answer.equals("y"))
                {
                    doctor.appointments.add(appointment);
                    break;
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

    public void displayProcedures(Doctor doctor)
    {
        for (Appointment appointment: doctor.appointments) {
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

    public void doAppointment(Doctor doctor, Appointment appointment)
    {
        try {
            System.out.println("Doctor " + doctor.getLastName() + " proceed with appointment.");
            System.out.println("----------------------------");
            System.out.println("Starting appointment.");
            String answer;
            Scanner sc = new Scanner(System.in);
            System.out.println(appointment.medicalProcedure);
            System.out.println("Start time (first hours, then minutes, leave empty for current time): ");
            String aux = sc.nextLine();
            Integer hours;
            Integer minutes;
            if (aux.equals("\n")) {
                hours = LocalTime.now().getHour();
                minutes = LocalTime.now().getMinute();
            } else {
                hours = Integer.parseInt(aux);
                minutes = Integer.parseInt(sc.nextLine());
            }
            appointment.medicalProcedure.setStartTime(LocalTime.of(hours, minutes));


            if (appointment.medicalProcedure instanceof Checkup) {
                System.out.println("Diagnose? (Y/N)");
                answer = sc.nextLine();
                if (answer.equals("Y") || answer.equals("y")) {
                    Affection diagnose = this.diagnose(appointment.date);
                    ((Checkup) appointment.medicalProcedure).setDiagnosis(diagnose);
                    System.out.println("Add treatments? (Y/N)");
                    answer = sc.nextLine();
                    if (answer.equals("Y") || answer.equals("y")) {
                        ((Checkup) appointment.medicalProcedure).setTreatments(planTreatment(((Checkup) appointment.medicalProcedure).getDiagnosis()));
                    }
                }
                System.out.println("Observations: ");
                answer = sc.nextLine();
                ((Checkup) appointment.medicalProcedure).setObservations(answer);
                System.out.println("Checkup duration (first hours, then minutes, leave empty for default): ");
                aux = sc.nextLine();

                if (aux.equals("\n")) {
                    hours = 0;
                    minutes = 20;
                } else {
                    hours = Integer.parseInt(aux);
                    minutes = Integer.parseInt(sc.nextLine());
                }

                appointment.medicalProcedure.setDuration(LocalTime.of(hours, minutes));
            } else if (appointment.medicalProcedure instanceof Surgery) {
                System.out.println("Surgery risk: ");
                answer = sc.nextLine();
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
                ((Surgery) appointment.medicalProcedure).setRisk(risk);
                System.out.println("Complications: ");
                answer = sc.nextLine();
                ((Surgery) appointment.medicalProcedure).setComplications(answer);
                System.out.println("Duration (first hours, then minutes): ");
                hours = Integer.parseInt(sc.nextLine());
                minutes = Integer.parseInt(sc.nextLine());
                appointment.medicalProcedure.setDuration(LocalTime.of(hours, minutes));
            }

            appointment.setStatus(Status.DONE);
            System.out.println("Appointment done.");
            System.out.println("----------------------------");
        }
        catch (NumberFormatException ne)
        {
            System.out.println("Invalid number/date.");
        }
    }

    //TODO: add list of affections, find if it's there if not add it. else let pick from the list.
    private Affection diagnose(LocalDateTime startDate)
    {
        System.out.println("----------------------------");
        String name, severity_aux;
        Scanner sc = new Scanner(System.in);

        System.out.println("What's your diagnose?");
        System.out.printf("Name of affection: ");
        name = sc.nextLine();

        System.out.printf("Severity (low/medium/high/insignificant): ");
        severity_aux = sc.nextLine();

        Severity severity;
        switch (severity_aux)
        {
            case "low": severity = Severity.LOW; break;
            case "medium": severity = Severity.MEDIUM; break;
            case "high": severity = Severity.HIGH; break;
            default:
                severity = Severity.INSIGNIFICANT; break;
        }

        return new Affection(name, LocalDate.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth()), severity);
    }

    private List<Treatment> planTreatment(Affection diagnosis)
    {
        try {
            System.out.println("Treatment for " + diagnosis.getName());
            System.out.println("----------------------------");
            List<Treatment> treatments = new ArrayList<>();
            String answer;
            Scanner sc = new Scanner(System.in);
            do {
                String drug;
                System.out.printf("Drug name: ");
                drug = sc.nextLine();
                Integer numberOfDays;
                System.out.printf("Number of days for treatment: ");
                numberOfDays = Integer.parseInt(sc.nextLine());
                System.out.printf("Units per day: ");
                Float units;
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
