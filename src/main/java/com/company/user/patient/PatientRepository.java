package com.company.user.patient;

import com.company.database.DatabaseConfiguration;
import com.company.user.Gender;
import com.company.user.user.User;
import com.company.user.user.UserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PatientRepository {
    private static PatientRepository instance = null;

    private PatientRepository(){
    }

    public static PatientRepository getInstance()
    {
        if (instance == null)
        {
            instance = new PatientRepository();
        }
        return instance;
    }

    public void createTable() {
        String command = "create table if not exists patients(\n" +
                "\tid int primary key,\n" +
                "    firstName varchar(100) not null,\n" +
                "    lastName varchar(100) not null,\n" +
                "    dateOfBirth date not null,\n" +
                "    gender enum ('MALE', 'FEMALE'),\n" +
                "\tCONSTRAINT FK_patients_users FOREIGN KEY (id)\n" +
                "    REFERENCES users(id) ON DELETE CASCADE\n" +
                ");\n";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Patient patient)
    {
        String command = "insert into patients(id, firstName, lastName, dateOfBirth, gender)\n" +
                "values (?, ?, ?, ?, ?);";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, patient.getId());
            preparedStatement.setString(2, patient.getFirstName());
            preparedStatement.setString(3, patient.getLastName());
            preparedStatement.setDate(4, Date.valueOf(patient.getDateOfBirth()));
            preparedStatement.setString(5, patient.getGender().name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Patient> selectAll()
    {
        String command = "select * from patients;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(command);

            return mapToPatients(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Patient> mapToPatients(ResultSet resultSet) throws SQLException {
        List<Patient> result = new ArrayList<>();
        while (resultSet.next()) {
            User correspondingUser = UserRepository.getInstance().getUserById(resultSet.getInt(1));
            result.add(new Patient(correspondingUser.getId(), correspondingUser.getUsername(), correspondingUser.getEmail(), correspondingUser.getPassword(),
                    resultSet.getString(2), resultSet.getString(3),
                    LocalDate.parse(resultSet.getDate(4).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    Gender.valueOf(resultSet.getString(5))));
        }

        return result;
    }

}
