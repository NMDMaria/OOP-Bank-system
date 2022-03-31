package com.company;

import com.company.administrative.User;
import com.company.administrative.UserService;
import com.company.medicine.Appointment;
import com.company.medicine.Checkup;
import com.company.medicine.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
        Pacient p = new Pacient("1", "1", "1", "1", "~", LocalDate.now(), Gender.FEMALE);
        System.out.println(p.toString());

        PacientService ps = new PacientService(p);
        ps.makeAppointment(LocalDateTime.now(), "smthg", "checkup");
        System.out.println(ps.findAppointment(LocalDateTime.now()));
        System.out.println(p.getAppointments().get(ps.findAppointment(LocalDateTime.now())));
        ps.viewAppointments(2022, 3, 30);

        System.out.println("----------------------------");
        Doctor d = new Doctor("2", "2", "2", "2", "2", LocalDate.now(), LocalDate.now(), "2", 1.0, "doc", "spec");

        DoctorService ds = new DoctorService(d);
        ds.selectAppointment(p.getAppointments());
        ds.displayProcedures();
        System.out.println(d.getAppointments().get(0));
        */
        /*
        UserService u = UserService.getInstance();
        System.out.println("Creating an patient account");
        Patient p = u.readPacient();
        System.out.println(p);
        System.out.println("----------------------------");

        System.out.println("Creating an doctor account");
        Doctor d = u.readDoctor();
        System.out.println(d);
        System.out.println("----------------------------");

        PacientService ps = new PacientService(p);
        System.out.println("Pacient makes appointment:");
        ps.makeAppointment(LocalDateTime.now(), "smthg", "checkup");
        System.out.println(p.getAppointments().get(ps.findAppointment(LocalDateTime.now())));


        System.out.println("----------------------------");
        ps.viewAppointments(2022, 3, 30);
        System.out.println("----------------------------");


        System.out.println("Doctor does appointment");
        System.out.println("----------------------------");
        DoctorService ds = new DoctorService(d);
        ds.selectAppointment(p.getAppointments());
        ds.displayProcedures();
        System.out.println("----------------------------");
        ds.displayProcedures();

        Scanner sc = new Scanner(System.in);
        UserService u = UserService.getInstance();
        u.readDoctor(sc);

        System.out.println(u.getUsers().get(0) instanceof Patient);
         */

        UserService u = UserService.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("🏥 Welcome to Sirona centre 🏥");
        String option;
        do {
            System.out.println("1. Enter user from keyboard");
            System.out.println("2. Connect to user");
            System.out.println("3. Find user");
            System.out.println("0. Exit");

            option = scanner.nextLine();

            switch (option)
            {
                case "1":{
                    System.out.println("1. Pacient");
                    System.out.println("2. Doctor");
                    String type = scanner.nextLine();
                    switch (type)
                    {
                        case "1":
                            u.readPacient(scanner);
                            break;
                        case "2":
                            u.readDoctor(scanner);
                            break;
                        default:
                            System.out.println("Invalid option.");
                    }
                    break;
                }
                case "2": {
                    u.connect(scanner);
                    break;
                }
                case "3": {
                    System.out.println("1. Find by username");
                    System.out.println("2. Find by email");
                    String type = scanner.nextLine();
                    switch (type)
                    {
                        case "1": {
                            System.out.println("Enter username: ");
                            String username = scanner.nextLine();
                            User user = u.findByUsername(username);
                            if (user == null)
                                System.out.println("No user found.");
                            else {
                                System.out.println(user);
                                System.out.println("Delete user? (Y/N)");
                                String deleteQuestion = scanner.nextLine();
                                if (deleteQuestion.equals("Y") || deleteQuestion.equals("y"))
                                    u.deleteUser(user);
                            }
                            break;
                        }
                        case "2": {
                            System.out.println("Enter username: ");
                            String email = scanner.nextLine();
                            User user = u.findByEmail(email);
                            if (user == null)
                                System.out.println("No user found.");
                            else {
                                System.out.println(user);
                                System.out.println("Delete user? (Y/N)");
                                String deleteQuestion = scanner.nextLine();
                                if (deleteQuestion.equals("Y") || deleteQuestion.equals("y"))
                                    u.deleteUser(user);
                            }
                            break;
                        }
                        default:
                            System.out.println("Invalid option");
                    }
                    break;
                }
            }
        }while (!option.equals("0"));
    }
}
