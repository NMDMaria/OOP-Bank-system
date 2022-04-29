package com.company.app;

import com.company.appointment.Appointment;
import com.company.appointment.AppointmentService;
import com.company.audit.AuditService;
import com.company.procedure.affliction.Affliction;
import com.company.procedure.affliction.AfflictionService;
import com.company.procedure.checkup.Checkup;
import com.company.procedure.checkup.CheckupService;
import com.company.procedure.medicalprocedure.MedicalProcedure;
import com.company.procedure.medicalprocedure.MedicalProcedureService;
import com.company.procedure.surgery.Surgery;
import com.company.procedure.surgery.SurgeryService;
import com.company.procedure.treatment.Treatment;
import com.company.procedure.treatment.TreatmentService;
import com.company.user.doctor.Doctor;
import com.company.user.doctor.DoctorService;
import com.company.user.patient.Patient;
import com.company.user.patient.PatientService;
import com.company.user.user.User;
import com.company.user.user.UserService;
import com.company.utils.CSVReader;
import com.company.utils.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class App {
    private List<User> users;
    private List<Doctor> doctors;
    private List<Patient> patients;
    private List<Appointment> appointments;
    private List<MedicalProcedure> medicalProcedures;
    private List<Checkup> checkups;
    private List<Surgery> surgeries;
    private List<Affliction> afflictions;
    private List<Treatment> treatments;
    private AuditService audit;

    private static App instance = null;

    private App(){
        audit = AuditService.getInstance();
    }

    public static App getInstance()
    {
        if (instance == null)
        {
            instance = new App();
        }
        return instance;
    }

    public void initialize()
    {
        users = new ArrayList<>();
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();
        medicalProcedures = new ArrayList<>();
        checkups = new ArrayList<>();
        surgeries = new ArrayList<>();
        afflictions = new ArrayList<>();
        treatments = new ArrayList<>();
    }

    public boolean initialize(String directoryPath) {
        try {
            audit.write("Initializing.");
            this.initialize();
            List<String> orderInit = Arrays.asList("patients", "doctors", "afflictions", "checkups", "surgeries", "treatments","appointments");

            File directory = new File(directoryPath);

            List<File> csvFiles = Arrays.asList(directory.listFiles());

            for (String current : orderInit) {
                File file = csvFiles.stream().filter(x -> x.getName().equals(current + ".csv")).findFirst().orElse(null);
                if (file != null) {
                    switch (current) {
                        case "patients": {
                            CSVReader<Patient> reader = new CSVReader<Patient>();
                            patients = reader.fromCSV(directory + "\\" + file.getName(), Patient.class,Boolean.TRUE);
                            users.addAll(patients);
                            if (UserService.getInstance().checkUsers(users) == false)
                                throw new Exception("Invalid user data.");
                            UserService.userKeyGenerator.updateKey(users.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                        case "doctors": {
                            CSVReader<Doctor> reader = new CSVReader<Doctor>();
                            doctors = reader.fromCSV(directory + "\\" + file.getName(), Doctor.class,Boolean.TRUE);
                            users.addAll(doctors);
                            if (UserService.getInstance().checkUsers(users) == false)
                                throw new Exception("Invalid user data.");
                            UserService.userKeyGenerator.updateKey(users.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                        case "afflictions": {
                            CSVReader<Affliction> reader = new CSVReader<Affliction>();
                            afflictions = reader.fromCSV(directory + "\\" + file.getName(), Affliction.class,Boolean.TRUE);
                            if (AfflictionService.getInstance().checkAfflictions(afflictions, patients) == false)
                                throw new Exception("Invalid affliction data.");
                            PatientService.getInstance().updateAfflictions(patients, afflictions);
                            AfflictionService.afflictionKeyGenerator.updateKey(afflictions.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                        case "checkups": {
                            CSVReader<Checkup> reader = new CSVReader<Checkup>();
                            checkups = reader.fromCSV(directory + "\\" + file.getName(), Checkup.class,Boolean.TRUE);
                            medicalProcedures.addAll(checkups);
                            if (MedicalProcedureService.getInstance().checkProcedures(medicalProcedures) == false)
                                throw new Exception("Invalid affliction data.");
                            CheckupService.getInstance().updateAffliction(checkups,afflictions);
                            MedicalProcedureService.medicalProcedureKeyGenerator.updateKey(medicalProcedures.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                        case "surgeries": {
                            CSVReader<Surgery> reader = new CSVReader<Surgery>();
                            surgeries = reader.fromCSV(directory + "\\" + file.getName(), Surgery.class,Boolean.TRUE);
                            medicalProcedures.addAll(surgeries);
                            if (MedicalProcedureService.getInstance().checkProcedures(medicalProcedures) == false)
                                throw new Exception("Invalid affliction data.");
                            MedicalProcedureService.medicalProcedureKeyGenerator.updateKey(medicalProcedures.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                        case "appointments": {
                            CSVReader<Appointment> reader = new CSVReader<Appointment>();
                            appointments = reader.fromCSV(directory + "\\" + file.getName(), Appointment.class,Boolean.TRUE);
                            if (AppointmentService.getInstance().checkAppointments(appointments, doctors) == false)
                                throw new Exception("Appointment references non existent doctor record.");
                            if (AppointmentService.getInstance().linkedProcedure(appointments, medicalProcedures) == false)
                                throw new Exception("Procedure made for appointment that doesn't exist.");
                            DoctorService.getInstance().updateAppointments(doctors, appointments);
                            PatientService.getInstance().updateAppointments(patients, appointments);
                            AppointmentService.getInstance().updateProcedure(appointments, medicalProcedures);
                            AppointmentService.appointmentKeyGenerator.updateKey(appointments.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                        case "treatments": {
                            CSVReader<Treatment> reader = new CSVReader<Treatment>();
                            treatments = reader.fromCSV(directory + "\\" + file.getName(), Treatment.class,Boolean.TRUE);
                            if (TreatmentService.getInstance().checkTreatments(treatments) == false)
                                throw new Exception("Invalid data.");
                            CheckupService.getInstance().updateTreatments(checkups, treatments);
                            TreatmentService.treatmentKeyGenerator.updateKey(treatments.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                    }
                } else {
                    throw new Exception("Not enough files to initialize.");
                }
            }
            return true;
        } catch (Exception e) {
            audit.write("Init error:" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void writeData(String directoryPath)
    {
        audit.write("Writing data");
        CSVWriter<Patient> patientCSVWriter = new CSVWriter<>();
        patientCSVWriter.toCSV(directoryPath, patients, Patient.class, true);
        CSVWriter<Doctor> doctorCSVWriter = new CSVWriter<>();
        doctorCSVWriter.toCSV(directoryPath, doctors, Doctor.class, true);
        CSVWriter<Affliction> afflictionCSVWriter = new CSVWriter<>();
        afflictionCSVWriter.toCSV(directoryPath, afflictions, Affliction.class, true);
        CSVWriter<Appointment> appointmentCSVWriter = new CSVWriter<>();
        appointmentCSVWriter.toCSV(directoryPath, appointments, Appointment.class, true);
        CSVWriter<Checkup> checkupCSVWriter = new CSVWriter<>();
        checkupCSVWriter.toCSV(directoryPath, checkups, Checkup.class, true);
        CSVWriter<Surgery> surgeryCSVWriter = new CSVWriter<>();
        surgeryCSVWriter.toCSV(directoryPath, surgeries, Surgery.class, true);
        CSVWriter<Treatment> treatmentCSVWriter = new CSVWriter<>();
        treatmentCSVWriter.toCSV(directoryPath, treatments, Treatment.class, true);

    }

    public void menu()
    {
        audit.write("Opening menu");
        if (!this.initialize("./data"))
            return;
        UserService userService = UserService.getInstance();
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
                    User newUser = userService.readUser(scanner);
                    if (newUser instanceof Patient) {
                        audit.write("New patient from keyboard");
                        patients.add((Patient) newUser);
                    } else if (newUser instanceof Doctor){
                        audit.write("New doctor from keyboard");
                        doctors.add((Doctor) newUser);
                    }
                    break;
                }
                case "2": {
                    audit.write("Connecting to user");
                    userService.connect(scanner);
                    break;
                }
                case "3": {
                    audit.write("Finding user");
                    System.out.println("1. Find by username");
                    System.out.println("2. Find by email");
                    String type = scanner.nextLine();
                    switch (type)
                    {
                        case "1": {
                            System.out.println("Enter username: ");
                            String username = scanner.nextLine();
                            User user = userService.findByUsername(username);
                            if (user == null)
                                System.out.println("No user found.");
                            else {
                                System.out.println(user);
                                System.out.println("Delete user? (Y/N)");
                                String deleteQuestion = scanner.nextLine();
                                if (deleteQuestion.equals("Y") || deleteQuestion.equals("y"))
                                    userService.deleteUser(user);
                                audit.write("Delete user");
                            }
                            break;
                        }
                        case "2": {
                            System.out.println("Enter email: ");
                            String email = scanner.nextLine();
                            User user = userService.findByEmail(email);
                            if (user == null)
                                System.out.println("No user found.");
                            else {
                                System.out.println(user);
                                System.out.println("Delete user? (Y/N)");
                                String deleteQuestion = scanner.nextLine();
                                if (deleteQuestion.equals("Y") || deleteQuestion.equals("y"))
                                    userService.deleteUser(user);
                                audit.write("Delete user");
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

        // Keeping changes in the files.
        this.writeData("./data");
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<MedicalProcedure> getMedicalProcedures() {
        return medicalProcedures;
    }

    public void setMedicalProcedures(List<MedicalProcedure> medicalProcedures) {
        this.medicalProcedures = medicalProcedures;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    public List<Affliction> getAfflictions() {
        return afflictions;
    }

    public void setAfflictions(List<Affliction> afflictions) {
        this.afflictions = afflictions;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Checkup> getCheckups() {
        return checkups;
    }

    public void setCheckups(List<Checkup> checkups) {
        this.checkups = checkups;
    }

    public List<Surgery> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(List<Surgery> surgeries) {
        this.surgeries = surgeries;
    }
}
