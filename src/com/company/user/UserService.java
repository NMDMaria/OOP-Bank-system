package com.company.user;

import com.company.appointment.Appointment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private List<User> users = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private static UserService instance;

    private UserService() {
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
            if (user.getUsername().equals(username))
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

    public User readUser(Scanner scanner) {
        try {
            System.out.println("----------------------------");
            System.out.println("Reading user");

            System.out.print("Username: ");
            String username = scanner.nextLine();
            if (this.findByUsername(username) != null)
                throw new Exception("An user with this username already exists.");

            System.out.print("Email: ");
            String email = scanner.nextLine();
            if (this.findByEmail(email) != null)
                throw new Exception("An user with this email already exists.");

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.print("Type (0 for Patient, 1 for Doctor): ");
            int type = scanner.nextInt();
            User newUser;
            switch (type) {
                case 0:
                    scanner.nextLine();
                    newUser = PatientService.readPatient(username, email, password, scanner);
                    this.users.add(newUser);
                    return newUser;
                case 1:
                    scanner.nextLine();
                    newUser = DoctorService.readDoctor(username, email, password, scanner);
                    this.users.add(newUser);
                    return newUser;
                default:
                    System.out.println("Invalid option");
                    return null;
            }

        } catch (NumberFormatException ne)
        {
            System.out.println("Invalid number/date.");
            return null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void connect(Scanner scanner)
    {
        try {
            System.out.println("----------------------------");
            System.out.println("Login");
            System.out.println("----------------------------");

            System.out.print("Username: ");
            String username = scanner.nextLine();
            User user = this.findByUsername(username);
            if (user == null)
                throw new Exception("User doesn't exist.");

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (!password.equals(user.getPassword()))
                throw new Exception("Incorrect password.");
            else {
                if (user instanceof Doctor) DoctorService.doctorMenu((Doctor) user, scanner, appointments);
                else if (user instanceof Patient) PatientService.patientMenu((Patient) user, scanner, appointments);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(User user)
    {
        this.users.remove(user);
    }
}
