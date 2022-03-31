package com.company.administrative;

import com.company.medicine.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private List<User> users = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private static UserService instance;
    private DoctorService doctorService;
    private PatientService patientService;

    private UserService() {
        doctorService = DoctorService.getInstance();
        patientService = PatientService.getInstance();
    }

    public static UserService getInstance()
    {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    public List<User> getUsers()
    {
        return new ArrayList<>(users);
    }

    public void addUser(User user)
    {
        this.users.add(user);
    }

    public User findByUsername(String username)
    {
        for (User user:this.users) {
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public User findByEmail(String username)
    {
        for (User user:this.users) {
            if (user.getEmail().equals(username))
                return user;
        }
        return null;
    }

    public Patient readPacient(Scanner scanner){
        try {
            System.out.println("----------------------------");
            System.out.println("Reading patient user");

            System.out.printf("Username: ");
            String username = scanner.nextLine();
            if (this.findByUsername(username) != null)
                throw new Exception("An user with this username already exists.");

            System.out.printf("Email: ");
            String email = scanner.nextLine();
            if (this.findByEmail(email) != null)
                throw new Exception("An user with this email already exists.");

            System.out.printf("Password: ");
            String password = scanner.nextLine();

            System.out.printf("First name: ");
            String firstName = scanner.nextLine();
            System.out.printf("Last name: ");
            String lastName = scanner.nextLine();

            System.out.printf("Date of birth (DD/MM/YYYY): ");
            String[] aux_dob = scanner.nextLine().split("/"); // aux date of birth
            if (aux_dob.length != 3)
                throw new Exception("Birthdate invalid.");
            LocalDate dob = LocalDate.of(Integer.parseInt(aux_dob[2]), Integer.parseInt(aux_dob[1]), Integer.parseInt(aux_dob[0])); // date of birth

            System.out.printf("Gender (M/F): ");
            String aux_gender = scanner.nextLine();
            Gender gender;
            switch (aux_gender) {
                case "M":
                    gender = Gender.MALE;
                    break;
                case "F":
                    gender = Gender.FEMALE;
                    break;
                case "m":
                    gender = Gender.MALE;
                    break;
                case "f":
                    gender = Gender.FEMALE;
                    break;
                default:
                    throw new Exception("Invalid gender option.");
            }

            Patient patient = new Patient(username, email, password, firstName, lastName, dob, gender);
            this.users.add(patient);

            return patient;
        }
        catch (NumberFormatException ne)
        {
            System.out.println("Invalid number/date.");
            return null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Doctor readDoctor(Scanner scanner)
    {
        try {
            System.out.println("----------------------------");
            System.out.println("Reading doctor user");

            System.out.printf("Username: ");
            String username = scanner.nextLine();
            if (this.findByUsername(username) != null)
                throw new Exception("An user with this username already exists.");

            System.out.printf("Email: ");
            String email = scanner.nextLine();
            if (this.findByEmail(email) != null)
                throw new Exception("An user with this email already exists.");

            System.out.printf("Password: ");
            String password = scanner.nextLine();

            System.out.printf("First name: ");
            String firstName = scanner.nextLine();

            System.out.printf("Last name: ");
            String lastName = scanner.nextLine();

            System.out.printf("Date of birth (DD/MM/YYYY): ");
            String[] aux_d = scanner.nextLine().split("/");
            if (aux_d.length != 3)
                throw new Exception("Birthdate invalid.");
            LocalDate dob = LocalDate.of(Integer.parseInt(aux_d[2]), Integer.parseInt(aux_d[1]), Integer.parseInt(aux_d[0]));

            System.out.printf("Date of employment (DD/MM/YYYY): ");
            aux_d = scanner.nextLine().split("/");
            if (aux_d.length != 3)
                throw new Exception("Birthdate invalid.");
            LocalDate doe = LocalDate.of(Integer.parseInt(aux_d[2]), Integer.parseInt(aux_d[1]), Integer.parseInt(aux_d[0]));

            System.out.printf("Phone number: ");
            String phoneNumber = scanner.nextLine();
            if (!phoneNumber.matches("^[0-9]*$"))
                throw new Exception("Phone number invalid.");

            System.out.printf("Salary: ");
            Double salary = Double.parseDouble(scanner.nextLine());

            System.out.printf("Job title: ");
            String jobName = scanner.nextLine();

            System.out.printf("Specialization: ");
            String specialization = scanner.nextLine();

            Doctor doctor = new Doctor(username, email, password, firstName, lastName, dob, doe, phoneNumber, salary, jobName, specialization);
            this.users.add(doctor);

            return doctor;
        }
        catch (NumberFormatException ne)
        {
            System.out.println("Invalid number/date.");
            return null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // TODO: let user try password 3 times, else exception
    public void connect(Scanner scanner)
    {
        try {
            System.out.println("----------------------------");
            System.out.println("Login");
            System.out.println("----------------------------");

            System.out.printf("Username: ");
            String username = scanner.nextLine();
            User user = this.findByUsername(username);
            if (user == null)
                throw new Exception("User doesn't exist.");

            System.out.printf("Password: ");
            String password = scanner.nextLine();

            if (!password.equals(user.getPassword()))
                throw new Exception("Incorrect password.");
            else {
                if (user instanceof Doctor) doctorMenu((Doctor) user, scanner);
                else if (user instanceof Patient) patientMenu((Patient) user, scanner);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void doctorMenu(Doctor doctor, Scanner scanner)
    {
        System.out.println("Welcome dr." + doctor.getLastName() + " " + doctor.getFirstName());

        String answer;
        do {
            System.out.println("1. Select appointments");
            System.out.println("2. Display waiting procedures");
            System.out.println("0. Exit");

            answer = scanner.nextLine();
            switch (answer) {
                case "1": {
                    doctorService.selectAppointment(doctor, this.appointments);
                    break;
                }
                case "2": {
                    doctorService.displayProcedures(doctor);
                    break;
                }
            }

        }while(!answer.equals("0"));
}

    private void patientMenu(Patient patient, Scanner scanner) {
        System.out.println("Welcome " + patient.getLastName() + " " + patient.getFirstName());

        String answer;
        do {
            System.out.println("1. Make an appointment");
            System.out.println("2. Show appointments by date");
            System.out.println("3. Move appointment");
            System.out.println("4. Cancel appointment");
            System.out.println("0. Exit");

            answer = scanner.nextLine();
            switch (answer) {
                case "1": {
                    try {
                        Appointment appointment = patientService.makeAppointment(patient, scanner);
                        this.appointments.add(appointment);
                    } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "2": {
                    String[] aux_date;

                    System.out.println("1. Full date (DD/MM/YYYY)");
                    System.out.println("2. Month and year (MM/YYYY)");
                    System.out.println("3. Year (YYYY)");

                    String secondOption = scanner.nextLine();
                    switch (secondOption) {
                        case "1": {
                            try {
                                aux_date = scanner.nextLine().split("/");
                                if (aux_date.length != 3)
                                    throw new Exception("Date invalid.");

                                patientService.viewAppointments(patient, Integer.parseInt(aux_date[2]), Integer.parseInt(aux_date[1]), Integer.parseInt(aux_date[0]));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case "2": {
                            try {
                                aux_date = scanner.nextLine().split("/");
                                if (aux_date.length != 2)
                                    throw new Exception("Date invalid.");
                                patientService.viewAppointments(patient, Integer.parseInt(aux_date[1]), Integer.parseInt(aux_date[0]));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                        case "3": {
                            try {
                                aux_date = scanner.nextLine().split("/");
                                if (aux_date.length != 1)
                                    throw new Exception("Date invalid.");
                                patientService.viewAppointments(patient, Integer.parseInt(aux_date[0]));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        }
                    }

                    break;
                }
                case "3": {
                    try{
                        System.out.println("What's the date for the appointment you want to move?");

                        System.out.printf("Date (DD/MM/YYYY): ");
                        String[] aux_date, aux_time;
                        aux_date = scanner.nextLine().split("/");
                        if (aux_date.length != 3)
                            throw new Exception("Date invalid.");

                        System.out.printf("Time (HH:MM): ");
                        aux_time = scanner.nextLine().split(" ");
                        if (aux_time.length != 2)
                            throw new Exception("Time invalid.");

                        LocalDateTime old_date = LocalDateTime.of(Integer.parseInt(aux_date[2]), Integer.parseInt(aux_date[1]),
                                Integer.parseInt(aux_date[0]), Integer.parseInt(aux_time[0]), Integer.parseInt(aux_time[1]));

                        System.out.println("What's the NEW date for the appointment you want to move?");

                        System.out.printf("Date (DD/MM/YYYY): ");
                        aux_date = scanner.nextLine().split("/");
                        if (aux_date.length != 3)
                            throw new Exception("Date invalid.");

                        System.out.printf("Time (HH:MM): ");
                        aux_time = scanner.nextLine().split(" ");
                        if (aux_time.length != 2)
                            throw new Exception("Time invalid.");

                        LocalDateTime new_date = LocalDateTime.of(Integer.parseInt(aux_date[2]), Integer.parseInt(aux_date[1]),
                                Integer.parseInt(aux_date[0]), Integer.parseInt(aux_time[0]), Integer.parseInt(aux_time[1]));

                        patientService.moveAppointment(patient, old_date, new_date);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "4":{
                    try {
                        System.out.println("What's the date for the appointment you want to cancel?");

                        System.out.printf("Date (DD/MM/YYYY): ");
                        String[] aux_date, aux_time;
                        aux_date = scanner.nextLine().split("/");
                        if (aux_date.length != 3)
                            throw new Exception("Date invalid.");

                        System.out.printf("Time (HH MM): ");
                        aux_time = scanner.nextLine().split(" ");
                        if (aux_time.length != 2)
                            throw new Exception("Time invalid.");

                        LocalDateTime date = LocalDateTime.of(Integer.parseInt(aux_date[2]), Integer.parseInt(aux_date[1]),
                                Integer.parseInt(aux_date[0]), Integer.parseInt(aux_time[0]), Integer.parseInt(aux_time[1]));

                        patientService.cancelAppointment(patient, date);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            }
        }while(!answer.equals("0"));
    }

    public void deleteUser(User user)
    {
        this.users.remove(user);
    }

    public void mainMenu(Scanner scanner)
    {
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
                            readPacient(scanner);
                            break;
                        case "2":
                            readDoctor(scanner);
                            break;
                        default:
                            System.out.println("Invalid option.");
                    }
                    break;
                }
                case "2": {
                    connect(scanner);
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
                            User user = findByUsername(username);
                            if (user == null)
                                System.out.println("No user found.");
                            else {
                                System.out.println(user);
                                System.out.println("Delete user? (Y/N)");
                                String deleteQuestion = scanner.nextLine();
                                if (deleteQuestion.equals("Y") || deleteQuestion.equals("y"))
                                    deleteUser(user);
                            }
                            break;
                        }
                        case "2": {
                            System.out.println("Enter username: ");
                            String email = scanner.nextLine();
                            User user = findByEmail(email);
                            if (user == null)
                                System.out.println("No user found.");
                            else {
                                System.out.println(user);
                                System.out.println("Delete user? (Y/N)");
                                String deleteQuestion = scanner.nextLine();
                                if (deleteQuestion.equals("Y") || deleteQuestion.equals("y"))
                                    deleteUser(user);
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
