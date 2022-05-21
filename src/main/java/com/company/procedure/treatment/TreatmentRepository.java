package com.company.procedure.treatment;

import com.company.database.DatabaseConfiguration;
import com.company.procedure.Severity;
import com.company.procedure.surgery.Surgery;
import com.company.procedure.surgery.SurgeryRepository;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TreatmentRepository {
    private static TreatmentRepository instance = null;

    private TreatmentRepository(){
    }

    public static TreatmentRepository getInstance()
    {
        if (instance == null)
        {
            instance = new TreatmentRepository();
        }
        return instance;
    }

    public void createTable() {
        String command = "create table if not exists  treatments(\n" +
                "\tid int primary key auto_increment,\n" +
                "\tmedicalProcedure int,\n" +
                "    drug varchar(100),\n" +
                "    numberOfDays int,\n" +
                "    units float,\n" +
                "\tCONSTRAINT FK_treatments_procedures FOREIGN KEY (medicalProcedure)\n" +
                "    REFERENCES medicalProcedures(id) ON DELETE CASCADE\n" +
                ");";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Treatment treatment)
    {
        String command = "insert into treatments(id, medicalProcedure, drug, numberOfDays, units) values (?, ?, ?, ?, ?);";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, treatment.getId());
            preparedStatement.setInt(2, treatment.getMedicalProcedure());
            preparedStatement.setString(3, treatment.getDrug());
            preparedStatement.setInt(4, treatment.getNumberOfDays());
            preparedStatement.setFloat(5, treatment.getUnits());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Treatment> selectAll()
    {
        String command = "select * from treatments;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(command);

            return mapToTreatments(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Treatment> mapToTreatments(ResultSet resultSet) throws SQLException {
        List<Treatment> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new Treatment(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3),
                resultSet.getInt(4), resultSet.getFloat(5)));
        }

        return result;
    }
}
