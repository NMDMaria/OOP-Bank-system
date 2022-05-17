package com.company.appointment;

import com.company.app.App;
import com.company.audit.AuditService;
import com.company.procedure.medicalprocedure.MedicalProcedure;
import com.company.user.doctor.Doctor;
import com.company.utils.KeyGenerator;

import java.util.*;

import static java.lang.Math.max;

public class AppointmentService {
    private static AppointmentService instance = null;
    public static KeyGenerator<Appointment> appointmentKeyGenerator = new KeyGenerator<>();

    private AppointmentService(){

    }

    public static AppointmentService getInstance()
    {
        if (instance == null)
        {
            instance = new AppointmentService();
        }
        return instance;
    }

    public boolean checkAppointments(List<Appointment> appointments, List<Doctor> doctors)
    {
        if (appointments.stream().map(Appointment::getId).distinct().count() != appointments.size())
            // Not all ids are distinct
            return false;
        for (Appointment appointment:appointments) {
            if (appointment.getPatient() == null || (appointment.getStatus() == Status.DONE && appointment.getDoctor() == null))
                // Check application constraints
                return false;
            if (appointment.getDoctor() != null && doctors.stream().filter(x->x.getId() == appointment.getDoctor()).count() != 1)
                // Doctor id should exist.
                return false;
        }
        return true;
    }

    public boolean linkedProcedure(List<Appointment> appointments, List<MedicalProcedure> medicalProcedures)
    {
        for (MedicalProcedure medicalProcedure:medicalProcedures) {
            if (appointments.stream().filter(x-> x.getId() == medicalProcedure.getAppointmentId()).count() != 1)
                return false;
        }
        return true;
    }

    public void updateProcedure(List<Appointment> appointments, List<MedicalProcedure> medicalProcedures)
    {
        try {
            for (MedicalProcedure procedure : medicalProcedures) {
                Appointment appointment = appointments.stream().filter(x->x.getId()==procedure.getAppointmentId()).findFirst().orElse(null);
                if (appointment == null)
                    throw new Exception("Incorrect appointment/procedure");
                else
                    appointment.setMedicalProcedure(procedure);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Appointment findById(List<Appointment> appointments, int appointmentid) {
        return appointments.stream().filter(x->x.getId() == appointmentid).findFirst().orElse(null);
    }
}
