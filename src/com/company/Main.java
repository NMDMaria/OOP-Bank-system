package com.company;

import com.company.Administrative.UserService;
import com.company.Medicine.Doctor;
import com.company.Medicine.DoctorService;
import com.company.Medicine.Patient;
import com.company.Medicine.PacientService;

import java.time.LocalDateTime;

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

    }
}
