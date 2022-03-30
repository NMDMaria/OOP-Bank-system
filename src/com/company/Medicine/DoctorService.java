package com.company.Medicine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class DoctorService {
    private final Doctor doctor;

    public DoctorService(Doctor doctor) {
        this.doctor = doctor;
    }

    public void selectAppointment(ArrayList<Appointment> appointments)
    {
        System.out.println("All waiting appointments are:");
        for (Appointment appointment: appointments) {
            if (appointment.getStatus() == Status.WAITING)
            {
                System.out.println(appointment.medical_procedure);
                System.out.println("Add appointment? (Y/N)");
                String answer;
                Scanner sc = new Scanner(System.in);
                answer = sc.nextLine();

                switch (answer)
                {
                    case "Y":
                    {
                        this.doctor.appointments.add(appointment);
                        break;
                    }
                }

                System.out.println("Continue to view? (Y/N)");
                answer = sc.nextLine();

                switch (answer)
                {
                    case "N":
                    {
                        return;
                    }
                }
            }
        }
        System.out.println("All waiting appointments were viewed.");
    }

    public void displayProcedures()
    {
        for (Appointment appointment: this.doctor.appointments) {
                System.out.println(appointment);
                if (appointment.status == Status.WAITING) {
                    System.out.println("Proceed?(Y/N)");
                    String answer;
                    Scanner sc = new Scanner(System.in);
                    answer = sc.nextLine();

                    switch (answer) {
                        case "Y": {
                            this.doAppointment(appointment);
                            break;
                        }
                    }
                }
        }
    }

    public void doAppointment(Appointment appointment)
    {
        String answer;
        Scanner sc = new Scanner(System.in);
        System.out.println(appointment.medical_procedure);
        System.out.println("Start time (first hours, then minutes): ");
        Integer hours = Integer.parseInt(sc.nextLine());
        Integer minutes = Integer.parseInt(sc.nextLine());
        appointment.medical_procedure.setStart_time(LocalTime.of(hours, minutes));


        if (appointment.medical_procedure instanceof Checkup) {
            System.out.println("Diagnose? (Y/N)");
            answer = sc.nextLine();
            if (answer.equals("Y")) {
                Affection diagnose = this.diagnose(appointment.date);
                ((Checkup) appointment.medical_procedure).setDiagnosis(diagnose);
                System.out.println("Add treatments? (Y/N)");
                answer = sc.nextLine();
                if (answer.equals("Y"))
                {
                    ((Checkup) appointment.medical_procedure).setTreatments(this.planTreatment(((Checkup) appointment.medical_procedure).diagnosis));
                }
            }
            System.out.println("Observations: ");
            answer = sc.nextLine();
            ((Checkup) appointment.medical_procedure).setObservations(answer);
            System.out.println("Checkup duration (first hours, then minutes): ");
            hours = Integer.parseInt(sc.nextLine());
            minutes = Integer.parseInt(sc.nextLine());
            appointment.medical_procedure.setDuration(LocalTime.of(hours, minutes));
        }
        else if (appointment.medical_procedure instanceof Surgery) {
            System.out.println("Surgery risk: ");
            answer = sc.nextLine();
            Severity risk;
            switch (answer)
            {
                case "low": risk = Severity.LOW; break;
                case "medium": risk = Severity.MEDIUM; break;
                case "high": risk = Severity.HIGH; break;
                default:
                   risk = Severity.INSIGNIFICANT; break;
            }
            ((Surgery) appointment.medical_procedure).setRisk(risk);
            System.out.println("Complications: ");
            answer = sc.nextLine();
            ((Surgery) appointment.medical_procedure).setComplications(answer);
            System.out.println("Duration (first hours, then minutes): ");
            hours = Integer.parseInt(sc.nextLine());
            minutes = Integer.parseInt(sc.nextLine());
            appointment.medical_procedure.setDuration(LocalTime.of(hours, minutes));
        }

        appointment.status = Status.DONE;
    }

    public Affection diagnose(LocalDateTime start_date)
    {
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

        return new Affection(name, LocalDate.of(start_date.getYear(), start_date.getMonthValue(), start_date.getDayOfMonth()), severity);
    }

    public ArrayList<Treatment> planTreatment(Affection diagnosis)
    {
        ArrayList<Treatment> treatments = new ArrayList<>();
        String answer;
        Scanner sc = new Scanner(System.in);
        do {
            String drug;
            System.out.printf("Drug name: ");
            drug = sc.nextLine();
            Integer number_days;
            System.out.printf("Number of days for treatment: ");
            number_days = Integer.parseInt(sc.nextLine());
            System.out.printf("Units per day: ");
            Float units;
            units = Float.parseFloat(sc.nextLine());

            Treatment treatment = new Treatment(drug, number_days, units);
            treatments.add(treatment);
            System.out.println("Add treatment (Y/N)");
            answer = sc.nextLine();
        }while(answer.equals("Y"));

        return treatments;
    }

}
