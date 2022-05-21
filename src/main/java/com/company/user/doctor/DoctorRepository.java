package com.company.user.doctor;

import com.company.database.DatabaseConfiguration;
import com.company.user.Gender;
import com.company.user.patient.Patient;
import com.company.user.patient.PatientRepository;
import com.company.user.user.User;
import com.company.user.user.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepository {
    private static DoctorRepository instance = null;

    private DoctorRepository(){
    }

    public static DoctorRepository getInstance()
    {
        if (instance == null)
        {
            instance = new DoctorRepository();
        }
        return instance;
    }

    public void createTable() {
        String command = "create table if not exists doctors(\n" +
                "\tid int primary key,\n" +
                "    firstName varchar(100) not null,\n" +
                "    lastName varchar(100) not null,\n" +
                "    dateOfBirth date not null,\n" +
                "\tdateOfEmployment date not null,\n" +
                "    phoneNumber varchar(12),\n" +
                "    salary double,\n" +
                "    jobName varchar(100),\n" +
                "    specialization varchar(100),\n" +
                "\tCONSTRAINT FK_doctors_users FOREIGN KEY (id)\n" +
                "    REFERENCES users(id) ON DELETE CASCADE\n" +
                ");";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Doctor doctor)
    {
        String command = "insert into doctors(id, firstName, lastName," +
                " dateOfBirth, dateOfEmployment, phoneNumber, salary, jobName, specialization)\n" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, doctor.getId());
            preparedStatement.setString(2, doctor.getFirstName());
            preparedStatement.setString(3, doctor.getLastName());
            preparedStatement.setDate(4, Date.valueOf(doctor.getDateOfBirth()));
            preparedStatement.setDate(5, Date.valueOf(doctor.getDateOfEmployment()));
            preparedStatement.setString(6, doctor.getPhoneNumber());
            preparedStatement.setDouble(7, doctor.getSalary());
            preparedStatement.setString(8, doctor.getJobName());
            preparedStatement.setString(9, doctor.getSpecialization());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Doctor> selectAll()
    {
        String command = "select * from doctors;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(command);

            return mapToDoctors(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Doctor> mapToDoctors(ResultSet resultSet) throws SQLException {
        List<Doctor> result = new ArrayList<>();
        while (resultSet.next()) {
            User correspondingUser = UserRepository.getInstance().getUserById(resultSet.getInt(1));
            result.add(new Doctor(correspondingUser.getId(), correspondingUser.getUsername(), correspondingUser.getEmail(), correspondingUser.getPassword(),
                    resultSet.getString(2), resultSet.getString(3),
                    LocalDate.parse(resultSet.getDate(4).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    LocalDate.parse(resultSet.getDate(5).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    resultSet.getString(6), resultSet.getDouble(7), resultSet.getString(8),
                    resultSet.getString(9)));
        }

        return result;
    }

}
