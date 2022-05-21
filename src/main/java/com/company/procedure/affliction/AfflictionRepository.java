package com.company.procedure.affliction;

import com.company.appointment.AppointmentRepository;
import com.company.database.DatabaseConfiguration;
import com.company.procedure.Severity;
import com.company.procedure.surgery.Surgery;
import com.company.user.Gender;
import com.company.user.patient.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AfflictionRepository {
    private static AfflictionRepository instance = null;

    private AfflictionRepository(){
    }

    public static AfflictionRepository getInstance()
    {
        if (instance == null)
        {
            instance = new AfflictionRepository();
        }
        return instance;
    }

    public void createTable() {
        String command = "create table if not exists afflictions(\n" +
                "\tid int primary key auto_increment,\n" +
                "    patientId int not null,\n" +
                "    name varchar(100) not null,\n" +
                "    checkupId int,\n" +
                "    startDate date not null,\n" +
                "    endDate date default null,\n" +
                "    severity enum( 'LOW','MEDIUM','HIGH','INSIGNIFICANT'),\n" +
                "    cured boolean,\n" +
                "\tCONSTRAINT FK_afflictions_patients FOREIGN KEY (patientId)\n" +
                "    REFERENCES patients(id) ON DELETE CASCADE\n" +
                ");";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Affliction affliction)
    {
        String command = "insert into afflictions(id, patientId, name, checkupId, startDate, " +
                "endDate, severity, cured) values (?, ?, ?, ?, ?, ?, ?, ?);";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, affliction.getId());
            preparedStatement.setInt(2, affliction.getPatientId());
            preparedStatement.setString(3, affliction.getName());
            if (affliction.getCheckup() == null)
                preparedStatement.setNull(4, Types.NULL);
            else
                preparedStatement.setInt(4, affliction.getCheckup());
            preparedStatement.setDate(5, Date.valueOf(affliction.getStartDate()));
            if (affliction.getEndDate() == null)
                preparedStatement.setNull(6, Types.NULL);
            else
                preparedStatement.setDate(6, Date.valueOf(affliction.getEndDate()));
            preparedStatement.setString(7, affliction.getSeverity().name());
            if (affliction.getCured() == null)
                preparedStatement.setBoolean(8, false);
            else
                preparedStatement.setBoolean(8, affliction.getCured());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Affliction> selectAll()
    {
        String command = "select * from afflictions;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(command);

            return mapToAfflictions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Affliction> mapToAfflictions(ResultSet resultSet) throws SQLException {
        List<Affliction> result = new ArrayList<>();
        while (resultSet.next()) {
            Integer checkupId = resultSet.getInt(4);
            if (resultSet.wasNull())
                checkupId = null;
            Date date = resultSet.getDate(6);
            LocalDate endDate = null;
            if (!resultSet.wasNull())
                endDate = date.toLocalDate();


            result.add(new Affliction(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
                    checkupId, resultSet.getDate(5).toLocalDate(),
                    endDate, Severity.valueOf(resultSet.getString(7)), resultSet.getBoolean(8)));
        }

        return result;
    }

}
