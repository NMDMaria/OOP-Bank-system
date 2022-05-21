package com.company.appointment;

import com.company.app.App;
import com.company.database.DatabaseConfiguration;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {
    private static AppointmentRepository instance = null;

    private AppointmentRepository() {
    }

    public static AppointmentRepository getInstance() {
        if (instance == null) {
            instance = new AppointmentRepository();
        }
        return instance;
    }

    public void createTable() {
        String command = "create table if not exists appointments(\n" +
                "\tid int primary key auto_increment,\n" +
                "\tdoctor int default null,\n" +
                "    patient int,\n" +
                "    date DateTime,\n" +
                "    status enum('WAITING','CANCELLED','DONE'),\n" +
                "\tspecialization varchar(100),\n" +
                "\tCONSTRAINT FK_appointments_patients FOREIGN KEY (patient)\n" +
                "    REFERENCES patients(id) ON DELETE CASCADE,\n" +
                "    CONSTRAINT FK_appointments_doctors FOREIGN KEY (doctor)\n" +
                "    REFERENCES doctors(id) ON DELETE CASCADE\n" +
                ");";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insert(Appointment appointment) {
        String command = "insert into appointments(id, doctor, patient, date, status, specialization) values (?, ?, ?, ?, ?, ?);";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, appointment.getId());
            if (appointment.getDoctor() != null)
                preparedStatement.setInt(2, appointment.getDoctor());
            else
                preparedStatement.setNull(2, Types.NULL);

            preparedStatement.setInt(3, appointment.getPatient());
            preparedStatement.setString(4, appointment.getDate().toString());
            preparedStatement.setString(5, appointment.getStatus().name());
            preparedStatement.setString(6, appointment.getSpecialization());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int id, Status newStatus)
    {
        String command = "update appointments set status = ? where id = ?;";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setString(1, newStatus.name());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Appointment appointment)
    {
        String command = "update appointments set doctor = ?, date = ?, status = ? where id = ?;";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            if (appointment.getDoctor() == null)
                preparedStatement.setNull(1, Types.NULL);
            else
                preparedStatement.setInt(1, appointment.getDoctor());
            preparedStatement.setString(2, appointment.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            preparedStatement.setString(3, appointment.getStatus().name());
            preparedStatement.setInt(4, appointment.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id)
    {
        String command = "delete from appointments where id = ?;";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Appointment> selectAll()
    {
        String command = "select * from appointments;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(command);

            return mapToAppointments(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Appointment> selectByDate(LocalDate date) {
        String processedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String command = "select * from appointments where date >= '" + processedDate + " 00:00:00'" + " AND date <= '"
                + processedDate + " 23:59:59';";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(command);

            return mapToAppointments(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Appointment> mapToAppointments(ResultSet resultSet) throws SQLException {
        List<Appointment> result = new ArrayList<>();
        while (resultSet.next()) {
            Integer doctor = resultSet.getInt(2);
            if (resultSet.wasNull()) {
                doctor = null;
            }
            result.add(new Appointment(resultSet.getInt(1), doctor, resultSet.getInt(3),
                    LocalDateTime.parse(resultSet.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    Status.valueOf(resultSet.getString(5)), resultSet.getString(6), null));
        }
        return result;
    }
}
