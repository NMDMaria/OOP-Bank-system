package com.company;

import com.company.Models.Appointment;
import com.company.Models.AppointmentService;
import com.company.Models.PacientService;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        PacientService p = new PacientService();
        AppointmentService a = new AppointmentService();

        Appointment app = a.makeAppointmentCheckup(LocalDateTime.now(), "a", "a");

    }
}
