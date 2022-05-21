package com.company;

import com.company.app.App;
import com.company.appointment.Appointment;
import com.company.appointment.AppointmentRepository;
import com.company.appointment.Status;
import com.company.user.Gender;
import com.company.user.doctor.Doctor;
import com.company.user.patient.Patient;
import com.company.user.patient.PatientRepository;
import com.company.user.user.User;
import com.company.user.user.UserRepository;
import com.company.utils.CSVReader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        App.getInstance().menu();
    }
}
