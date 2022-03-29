package com.company.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class PacientService {
    public ArrayList<Pacient> pacients = new ArrayList<>();

    public void addPacient(Pacient pacient)
    {
        this.pacients.add(pacient);

    }
    public void addAppointment(Pacient pacient, Appointment appointment)
    {
        for (Pacient pac: pacients) {
            if (pac.equals(pacient))
            {
                pac.appointments.add(appointment);
            }
        }
    }

    //TODO: make this but with date and search the array
    public void cancelAppointment(Appointment appointment)
    {
    }

    public void moveAppointment(Appointment appointment, LocalDateTime new_date)
    {
        
    }

    public void makeAppointment()
    {
        // get user input... and call the AppointmentService methods
    }

    public void medical_history() {
        // add affections list <-> call set affections after input
    }


}
