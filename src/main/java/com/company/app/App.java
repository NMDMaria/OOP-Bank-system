package com.company.app;

import com.company.appointment.Appointment;
import com.company.appointment.AppointmentRepository;
import com.company.appointment.AppointmentService;
import com.company.audit.AuditService;
import com.company.database.DatabaseConfiguration;
import com.company.procedure.affliction.Affliction;
import com.company.procedure.affliction.AfflictionRepository;
import com.company.procedure.affliction.AfflictionService;
import com.company.procedure.checkup.Checkup;
import com.company.procedure.checkup.CheckupRepository;
import com.company.procedure.checkup.CheckupService;
import com.company.procedure.medicalprocedure.MedicalProcedure;
import com.company.procedure.medicalprocedure.MedicalProcedureRepository;
import com.company.procedure.medicalprocedure.MedicalProcedureService;
import com.company.procedure.surgery.Surgery;
import com.company.procedure.surgery.SurgeryRepository;
import com.company.procedure.treatment.Treatment;
import com.company.procedure.treatment.TreatmentRepository;
import com.company.procedure.treatment.TreatmentService;
import com.company.user.doctor.Doctor;
import com.company.user.doctor.DoctorRepository;
import com.company.user.doctor.DoctorService;
import com.company.user.patient.Patient;
import com.company.user.patient.PatientRepository;
import com.company.user.patient.PatientService;
import com.company.user.user.User;
import com.company.user.user.UserRepository;
import com.company.user.user.UserService;
import com.company.utils.CSVReader;
import com.company.utils.CSVWriter;

import java.io.File;
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

    private boolean csvApp = false;
    private String csvFolder;
    private boolean databaseApp = false;

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

    private void initialize()
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

        initTables();
    }

    private void initTables()
    {
        UserRepository.getInstance().createTable();
        PatientRepository.getInstance().createTable();
        DoctorRepository.getInstance().createTable();
        AfflictionRepository.getInstance().createTable();
        AppointmentRepository.getInstance().createTable();
        MedicalProcedureRepository.getInstance().createTable();
        CheckupRepository.getInstance().createTable();
        SurgeryRepository.getInstance().createTable();
        TreatmentRepository.getInstance().createTable();

    }

    private void initializeFromDatabase()
    {
        try {
            initTables();
            List<String> orderInit = Arrays.asList("patients", "doctors", "afflictions", "checkups", "surgeries", "treatments", "appointments");

            for (String current : orderInit) {
                switch (current) {
                    case "patients": {
                        patients = PatientRepository.getInstance().selectAll();
                        users.addAll(patients);
                        if (UserService.getInstance().checkUsers(users) == false)
                            throw new Exception("Invalid user data.");
                        if (users.size() > 0)
                            UserService.userKeyGenerator.updateKey(users.stream().map(x -> x.getId()).max(Integer::compare).get());
                        break;
                    }
                    case "doctors": {
                        doctors = DoctorRepository.getInstance().selectAll();
                        users.addAll(doctors);
                        if (UserService.getInstance().checkUsers(users) == false)
                            throw new Exception("Invalid user data.");
                        if (users.size() > 0)
                            UserService.userKeyGenerator.updateKey(users.stream().map(x -> x.getId()).max(Integer::compare).get());
                        break;
                    }
                    case "afflictions": {
                        afflictions = AfflictionRepository.getInstance().selectAll();
                        if (AfflictionService.getInstance().checkAfflictions(afflictions, patients) == false)
                            throw new Exception("Invalid affliction data.");
                        PatientService.getInstance().updateAfflictions(patients, afflictions);
                        if (afflictions.size() > 0)
                            AfflictionService.afflictionKeyGenerator.updateKey(afflictions.stream().map(x -> x.getId()).max(Integer::compare).get());
                        break;
                    }
                    case "checkups": {
                        checkups = CheckupRepository.getInstance().selectAll();
                        medicalProcedures.addAll(checkups);
                        if (MedicalProcedureService.getInstance().checkProcedures(medicalProcedures) == false)
                            throw new Exception("Invalid affliction data.");
                        CheckupService.getInstance().updateAffliction(checkups, afflictions);
                        if (medicalProcedures.size() > 0)
                            MedicalProcedureService.medicalProcedureKeyGenerator.updateKey(medicalProcedures.stream().map(x -> x.getId()).max(Integer::compare).get());
                        break;
                    }
                    case "surgeries": {
                        surgeries = SurgeryRepository.getInstance().selectAll();
                        medicalProcedures.addAll(surgeries);
                        if (MedicalProcedureService.getInstance().checkProcedures(medicalProcedures) == false)
                            throw new Exception("Invalid affliction data.");
                        if (medicalProcedures.size() > 0)
                            MedicalProcedureService.medicalProcedureKeyGenerator.updateKey(medicalProcedures.stream().map(x -> x.getId()).max(Integer::compare).get());
                        break;
                    }
                    case "appointments": {
                        appointments = AppointmentRepository.getInstance().selectAll();
                        if (AppointmentService.getInstance().checkAppointments(appointments, doctors) == false)
                            throw new Exception("Appointment references non existent doctor record.");
                        if (AppointmentService.getInstance().linkedProcedure(appointments, medicalProcedures) == false)
                            throw new Exception("Procedure made for appointment that doesn't exist.");
                        DoctorService.getInstance().updateAppointments(doctors, appointments);
                        PatientService.getInstance().updateAppointments(patients, appointments);
                        AppointmentService.getInstance().updateProcedure(appointments, medicalProcedures);
                        if (appointments.size() > 0)
                            AppointmentService.appointmentKeyGenerator.updateKey(appointments.stream().map(x -> x.getId()).max(Integer::compare).get());
                        break;
                    }
                    case "treatments": {
                        treatments = TreatmentRepository.getInstance().selectAll();
                        if (TreatmentService.getInstance().checkTreatments(treatments) == false)
                            throw new Exception("Invalid data.");
                        CheckupService.getInstance().updateTreatments(checkups, treatments);
                        if (treatments.size() > 0)
                            TreatmentService.treatmentKeyGenerator.updateKey(treatments.stream().map(x -> x.getId()).max(Integer::compare).get());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean initializeFromCSV(String directoryPath) {
        // Initializing application objects using CSV reader
        // After reading the objects they are checked to conform to the application rules
        // and do other initializing like populating lists.
        // Also, the key generator is updated to match the last id found.
        try {
            List<String> orderInit = Arrays.asList("patients", "doctors", "afflictions", "checkups", "surgeries", "treatments","appointments");

            File directory = new File(directoryPath);
            if (!directory.exists())
                throw new Exception("Directory doesn't exist.");

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
                            if (users.size() > 0)
                                UserService.userKeyGenerator.updateKey(users.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                        case "doctors": {
                            CSVReader<Doctor> reader = new CSVReader<Doctor>();
                            doctors = reader.fromCSV(directory + "\\" + file.getName(), Doctor.class,Boolean.TRUE);
                            users.addAll(doctors);
                            if (UserService.getInstance().checkUsers(users) == false)
                                throw new Exception("Invalid user data.");
                            if (users.size() > 0)
                                UserService.userKeyGenerator.updateKey(users.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                        case "afflictions": {
                            CSVReader<Affliction> reader = new CSVReader<Affliction>();
                            afflictions = reader.fromCSV(directory + "\\" + file.getName(), Affliction.class,Boolean.TRUE);
                            if (AfflictionService.getInstance().checkAfflictions(afflictions, patients) == false)
                                throw new Exception("Invalid affliction data.");
                            PatientService.getInstance().updateAfflictions(patients, afflictions);
                            if (afflictions.size() > 0)
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
                            if (medicalProcedures.size() > 0)
                                MedicalProcedureService.medicalProcedureKeyGenerator.updateKey(medicalProcedures.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                        case "surgeries": {
                            CSVReader<Surgery> reader = new CSVReader<Surgery>();
                            surgeries = reader.fromCSV(directory + "\\" + file.getName(), Surgery.class,Boolean.TRUE);
                            medicalProcedures.addAll(surgeries);
                            if (MedicalProcedureService.getInstance().checkProcedures(medicalProcedures) == false)
                                throw new Exception("Invalid affliction data.");
                            if (medicalProcedures.size() > 0)
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
                            if (appointments.size() > 0)
                                AppointmentService.appointmentKeyGenerator.updateKey(appointments.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                        case "treatments": {
                            CSVReader<Treatment> reader = new CSVReader<Treatment>();
                            treatments = reader.fromCSV(directory + "\\" + file.getName(), Treatment.class,Boolean.TRUE);
                            if (TreatmentService.getInstance().checkTreatments(treatments) == false)
                                throw new Exception("Invalid data.");
                            CheckupService.getInstance().updateTreatments(checkups, treatments);
                            if (treatments.size() > 0)
                                TreatmentService.treatmentKeyGenerator.updateKey(treatments.stream().map(x->x.getId()).max(Integer::compare).get());
                            break;
                        }
                    }
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveToDatabase()
    {
        initTables();
        // Saving users
        List<User> memorizedUsers = UserRepository.getInstance().selectAll();
        int nrNewUsers = 0;
        for (User user: users) {
            if (UserRepository.getInstance().getUserById(user.getId()) == null)
            {
                // User was just created, need to add it.
                UserRepository.getInstance().insert(user);
                nrNewUsers += 1;
            }
        }
        if (memorizedUsers.size() != users.size() - nrNewUsers) // Users got deleted
        {
            for (User user: memorizedUsers)
            {
                if (users.stream().noneMatch(x->x.getId() == user.getId()))
                {
                    UserRepository.getInstance().delete(user.getId());
                }
            }
        }


        List<Patient> memorizedPatients = PatientRepository.getInstance().selectAll();
        for (Patient patient: patients) {
            if (memorizedPatients.stream().noneMatch(x->x.getId() == patient.getId()))
            {
                PatientRepository.getInstance().insert(patient);
            }
        }

        List<Doctor> memorizedDoctors = DoctorRepository.getInstance().selectAll();
        for (Doctor doctor: doctors) {
            if (memorizedDoctors.stream().noneMatch(x->x.getId() == doctor.getId()))
            {
                DoctorRepository.getInstance().insert(doctor);
            }
        }

        List<Appointment> memorizedAppointments = AppointmentRepository.getInstance().selectAll();
        for (Appointment appointment: appointments) {
            if (memorizedAppointments.stream().noneMatch(x->x.getId() == appointment.getId()))
            {
                AppointmentRepository.getInstance().insert(appointment);
            }
            else if (memorizedAppointments.stream().filter(x->x.getId() == appointment.getId()).findFirst().orElse(null) != appointment)
            {
                AppointmentRepository.getInstance().update(appointment);
            }
        }

        List<Affliction> memorizedAfflictions = AfflictionRepository.getInstance().selectAll();
        for (Affliction affliction:afflictions) {
            if (memorizedAfflictions.stream().noneMatch(x->x.getId() == affliction.getId()))
            {
                AfflictionRepository.getInstance().insert(affliction);
            }
        }

        List<MedicalProcedure> memorizedProcedures = MedicalProcedureRepository.getInstance().selectAll();
        for (MedicalProcedure procedure: medicalProcedures) {
            if (memorizedProcedures.stream().noneMatch(x->x.getId() == procedure.getId())) {
                MedicalProcedureRepository.getInstance().insert(procedure);
            }
            else if (memorizedProcedures.stream().filter(x->x.getId() == procedure.getId()).findFirst().orElse(null) != procedure){
                MedicalProcedureRepository.getInstance().update(procedure);
            }
        }

        List<Checkup> memorizedCheckups = CheckupRepository.getInstance().selectAll();
        for (Checkup checkup: checkups) {
            if (memorizedCheckups.stream().noneMatch(x->x.getId() == checkup.getId())) {
                CheckupRepository.getInstance().insert(checkup);
            }
            else if (memorizedCheckups.stream().filter(x->x.getId() == checkup.getId()).findFirst().orElse(null) != checkup) {
                CheckupRepository.getInstance().update(checkup);
            }
        }

        List<Surgery> memorizedSurgeries = SurgeryRepository.getInstance().selectAll();
        for (Surgery surgery: surgeries) {
            if (memorizedSurgeries.stream().noneMatch(x->x.getId() == surgery.getId())) {
                SurgeryRepository.getInstance().insert(surgery);
            }
            else if (memorizedSurgeries.stream().filter(x->x.getId() == surgery.getId()).findFirst().orElse(null) != surgery) {
                SurgeryRepository.getInstance().update(surgery);
            }
        }

        List<Treatment> memorizedTreatments = TreatmentRepository.getInstance().selectAll();
        for (Treatment treatment: treatments) {
            if (memorizedTreatments.stream().noneMatch(x->x.getId() == treatment.getId())) {
                TreatmentRepository.getInstance().insert(treatment);
            }
        }
    }

    public void writeData(String directoryPath)
    {
        // Writes all the data using CSV writer
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
        this.initialize();
        System.out.println("Please choose an initialization method: ");
        String option;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("1. From folder with CSV files");
            System.out.println("2. From PAO database");
            System.out.println("0. Exit");

            option = scanner.nextLine();

            switch (option)
            {
                case "1": {
                    this.csvApp = true;
                    System.out.println("Please enter path to folder: ");
                    this.csvFolder = scanner.nextLine();
                    this.initializeFromCSV(this.csvFolder);
                    option = "0";
                    break;
                }
                case "2": {
                    this.initializeFromDatabase();
                    this.databaseApp = true;
                    option = "0";
                    break;
                }
            }
        }while (!option.equals("0"));

        UserService userService = UserService.getInstance();
        System.out.println("🏥 Welcome to Sirona centre 🏥");
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
                        audit.write("read_patient");
                        patients.add((Patient) newUser);
                    } else if (newUser instanceof Doctor){
                        audit.write("read_doctor");
                        doctors.add((Doctor) newUser);
                    }
                    break;
                }
                case "2": {
                    audit.write("connect");
                    userService.connect(scanner);
                    break;
                }
                case "3": {
                    audit.write("find_user");
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
                                if (deleteQuestion.equals("Y") || deleteQuestion.equals("y")) {
                                    if (user instanceof Patient)
                                        patients.remove(user);
                                    else if (user instanceof Doctor)
                                        doctors.remove(user);
                                    userService.deleteUser(user);

                                    audit.write("delete_user");
                                }
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
                                if (deleteQuestion.equals("Y") || deleteQuestion.equals("y")) {
                                    userService.deleteUser(user);
                                    audit.write("delete_user");
                                }
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

        System.out.println("Please choose a method of saving: ");

        do {
            System.out.println("1. To CSV files in a folder");
            System.out.println("2. To database");
            System.out.println("0. Exit");

            option = scanner.nextLine();

            switch (option)
            {
                case "1": {
                    System.out.println("Please enter path to folder: ");
                    this.writeData(scanner.nextLine());
                    option = "0";
                    break;
                }
                case "2": {
                    this.saveToDatabase();
                    option = "0";
                    break;
                }
            }
        }while (!option.equals("0"));

        DatabaseConfiguration.closeDatabaseConnection();
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
