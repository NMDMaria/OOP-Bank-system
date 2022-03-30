package com.company.Administrative;

import com.company.Medicine.Doctor;
import com.company.Medicine.Gender;
import com.company.Medicine.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    public ArrayList<User> users = new ArrayList<>();
    private static UserService instance;
    private UserService() {}

    public static UserService getInstance()
    {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    public ArrayList<User> getUsers()
    {
        ArrayList<User> result = new ArrayList<>();
        result.addAll(users);
        return result;
    }

    public void addUser(User user)
    {
        this.users.add(user);
    }

    public User findbyusername(String username)
    {
        for (User user:this.users) {
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public Patient readPacient(){
        Scanner sc = new Scanner(System.in);
        System.out.printf("Username: ");
        String username = sc.nextLine();
        System.out.printf("Email: ");
        String email = sc.nextLine();
        System.out.printf("Password: ");
        String password = sc.nextLine();

        System.out.printf("First name: ");
        String first_name = sc.nextLine();
        System.out.printf("Last_name: ");
        String last_name = sc.nextLine();
        System.out.printf("Date of birth (DD/MM/YYYY): ");
        String[] aux_dob = sc.nextLine().split("/");
        if (aux_dob.length != 3)
            return null;
        LocalDate dob = LocalDate.of(Integer.parseInt(aux_dob[2]), Integer.parseInt(aux_dob[1]), Integer.parseInt(aux_dob[0]));
        System.out.printf("Gender (M/F)");
        String aux_gender = sc.nextLine();
        Gender gender;
        switch (aux_gender)
        {
            case "M":
                gender = Gender.MALE; break;
            case "F":
                gender = Gender.FEMALE; break;
            default:
                return null;
        }

        Patient patient = new Patient(username, email, password, first_name, last_name, dob, gender);
        this.users.add(patient);
        return patient;
    }

    public Doctor readDoctor()
    {
        Scanner sc = new Scanner(System.in);
        System.out.printf("Username: ");
        String username = sc.nextLine();
        System.out.printf("Email: ");
        String email = sc.nextLine();
        System.out.printf("Password: ");
        String password = sc.nextLine();

        System.out.printf("First name: ");
        String first_name = sc.nextLine();
        System.out.printf("Last_name: ");
        String last_name = sc.nextLine();
        System.out.printf("Date of birth (DD/MM/YYYY): ");
        String[] aux_d = sc.nextLine().split("/");
        if (aux_d.length != 3)
            return null;
        LocalDate dob = LocalDate.of(Integer.parseInt(aux_d[2]), Integer.parseInt(aux_d[1]), Integer.parseInt(aux_d[0]));
        System.out.printf("Date of employment (DD/MM/YYYY): ");
        aux_d = sc.nextLine().split("/");
        if (aux_d.length != 3)
            return null;
        LocalDate doe = LocalDate.of(Integer.parseInt(aux_d[2]), Integer.parseInt(aux_d[1]), Integer.parseInt(aux_d[0]));
        System.out.printf("Phone number: ");
        String phone_number = sc.nextLine();
        System.out.printf("Salary: ");
        Double salary = Double.parseDouble(sc.nextLine());
        System.out.printf("Job title: ");
        String job_name = sc.nextLine();
        System.out.printf("Specialization: ");
        String specialization = sc.nextLine();

        Doctor doctor = new Doctor(username, email, password, first_name, last_name, dob, doe, phone_number, salary, job_name, specialization);
        this.users.add(doctor);
        return doctor;
    }
}
