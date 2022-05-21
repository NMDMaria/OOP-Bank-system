package com.company.procedure.checkup;

import com.company.database.DatabaseConfiguration;
import com.company.procedure.affliction.AfflictionRepository;
import com.company.procedure.medicalprocedure.MedicalProcedure;
import com.company.procedure.medicalprocedure.MedicalProcedureRepository;
import com.company.user.Gender;
import com.company.user.patient.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckupRepository {
    private static CheckupRepository instance = null;

    private CheckupRepository(){
    }

    public static CheckupRepository getInstance()
    {
        if (instance == null)
        {
            instance = new CheckupRepository();
        }
        return instance;
    }

    public void createTable() {
        String command = "create table if not exists checkups(\n" +
                "\tid int primary key,\n" +
                "    motive varchar(150),\n" +
                "    observations varchar(150),\n" +
                "\tCONSTRAINT FK_checkup_procedures FOREIGN KEY (id)\n" +
                "    REFERENCES medicalProcedures(id) ON DELETE CASCADE\n" +
                ");\n";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Checkup checkup)
    {
        String command = "insert into checkups(id, motive, observations) values (?, ?, ?)";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, checkup.getId());
            preparedStatement.setString(2, checkup.getMotive());
            if (checkup.getObservations() == null)
                preparedStatement.setNull(3, Types.NULL);
            else
                preparedStatement.setString(3, checkup.getObservations());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Checkup checkup)
    {
        String command = "update checkups set motive = ?, observations = ? where id = ?;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setString(1, checkup.getMotive());
            preparedStatement.setString(2, checkup.getObservations());
            preparedStatement.setInt(3, checkup.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Checkup> selectAll()
    {
        String command = "select * from checkups;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(command);

            return mapToCheckups(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Checkup> mapToCheckups(ResultSet resultSet) throws SQLException {
        List<Checkup> result = new ArrayList<>();
        while (resultSet.next()) {
            MedicalProcedure correspondingProcedure = MedicalProcedureRepository.getInstance().getProcedureById(resultSet.getInt(1));
            result.add(new Checkup(resultSet.getInt(1),correspondingProcedure.getAppointmentId(), correspondingProcedure.getStartTime(),
                    correspondingProcedure.getDuration(), resultSet.getString(2), resultSet.getString(3)));
        }

        return result;
    }

}
