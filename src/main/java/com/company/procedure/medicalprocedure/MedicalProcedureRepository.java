package com.company.procedure.medicalprocedure;

import com.company.appointment.Appointment;
import com.company.database.DatabaseConfiguration;
import com.company.procedure.checkup.Checkup;
import com.company.procedure.checkup.CheckupRepository;
import com.company.user.doctor.Doctor;
import com.company.user.user.User;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MedicalProcedureRepository {
    private static MedicalProcedureRepository instance = null;

    private MedicalProcedureRepository(){
    }

    public static MedicalProcedureRepository getInstance()
    {
        if (instance == null)
        {
            instance = new MedicalProcedureRepository();
        }
        return instance;
    }

    public void createTable() {
        String command = "create table if not exists  medicalProcedures(\n" +
                "\tid int primary key auto_increment,\n" +
                "    appointmentId int not null,\n" +
                "\tstartTime time,\n" +
                "    duration time,\n" +
                "\tCONSTRAINT FK_procedures_appointments FOREIGN KEY (appointmentId)\n" +
                "    REFERENCES appointments(id) ON DELETE CASCADE\n" +
                ");";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(MedicalProcedure medicalProcedure)
    {
        String command = "insert into medicalProcedures(id, appointmentId, startTime, duration) values " +
                "(?, ?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, medicalProcedure.getId());
            preparedStatement.setInt(2, medicalProcedure.getAppointmentId());
            preparedStatement.setTime(3, Time.valueOf(medicalProcedure.getStartTime()));
            preparedStatement.setTime(4, Time.valueOf(medicalProcedure.getDuration()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MedicalProcedure> selectAll()
    {
        String command = "select * from medicalProcedures;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(command);

            return mapToProcedures(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(MedicalProcedure procedure)
    {
        String command = "update medicalProcedures set startTime = ?, duration = ? where id = ?;";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setTime(1, Time.valueOf(procedure.getStartTime()));
            preparedStatement.setTime(2, Time.valueOf(procedure.getDuration()));
            preparedStatement.setInt(3, procedure.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<MedicalProcedure> mapToProcedures(ResultSet resultSet) throws SQLException {
        List<MedicalProcedure> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new MedicalProcedure(resultSet.getInt(1), resultSet.getInt(2),
                    LocalTime.parse(resultSet.getTime(3).toString(), DateTimeFormatter.ofPattern("HH:mm:ss")),
                    LocalTime.parse(resultSet.getTime(4).toString(), DateTimeFormatter.ofPattern("HH:mm:ss"))));
        }

        return result;
    }


    public MedicalProcedure getProcedureById(int id)
    {
        String command = "select * from medicalProcedures where id = ?;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return mapToProcedure(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private MedicalProcedure mapToProcedure(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new MedicalProcedure(resultSet.getInt(1), resultSet.getInt(2),
                    LocalTime.parse(resultSet.getTime(3).toString(), DateTimeFormatter.ofPattern("HH:mm:ss")),
                    LocalTime.parse(resultSet.getTime(4).toString(), DateTimeFormatter.ofPattern("HH:mm:ss")));
        }

        return null;
    }
}
