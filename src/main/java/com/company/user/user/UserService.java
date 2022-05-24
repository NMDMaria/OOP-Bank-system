package com.company.user.user;

import com.company.app.App;
import com.company.user.doctor.Doctor;
import com.company.user.doctor.DoctorService;
import com.company.user.patient.Patient;
import com.company.user.patient.PatientService;
import com.company.utils.KeyGenerator;

import java.util.*;

import static java.lang.Math.max;

public class UserService{
    private static UserService instance;
    public static KeyGenerator<User> userKeyGenerator = new KeyGenerator<>();

    private UserService() {
    }

    public static UserService getInstance()
    {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }


    public void addUser(User user)
    {
        List<User> aux = App.getInstance().getUsers();
        aux.add(user);
        App.getInstance().setUsers(aux);
    }

    public User findByUsername(String username)
    {
        for (User user:App.getInstance().getUsers()) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public User findByEmail(String username)
    {
        for (User user:App.getInstance().getUsers()) {
            if (user.getEmail().equals(username))
                return user;
        }
        return null;
    }

    public User findById(Integer id)
    {
        for (User user:App.getInstance().getUsers()) {
            if (user.getId().equals(id))
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
                    newUser = PatientService.getInstance().readPatient(username, email, password, scanner);
                    if (newUser == null)
                        return null;
                    this.addUser(newUser);
                    return newUser;
                case 1:
                    scanner.nextLine();
                    newUser = DoctorService.getInstance().readDoctor(username, email, password, scanner);
                    if (newUser == null)
                        return null;
                    this.addUser(newUser);
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
                if (user instanceof Doctor) DoctorService.getInstance().doctorMenu((Doctor) user, scanner);
                else if (user instanceof Patient) PatientService.getInstance().patientMenu((Patient) user, scanner);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(User user)
    {
        List<User> aux = App.getInstance().getUsers();
        aux.remove(user);
        App.getInstance().setUsers(aux);
    }

    public boolean checkUsers(List<User> users)
    {
        if (users.stream().map(x->x.getId()).distinct().count() != users.size())
            return false;
        if (users.stream().map(x->x.getUsername()).distinct().count() != users.size())
            return false;
        if (users.stream().map(x->x.getEmail()).distinct().count() != users.size())
            return false;
        return true;
    }
}
