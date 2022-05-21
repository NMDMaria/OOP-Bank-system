package com.company.procedure.surgery;

import com.company.database.DatabaseConfiguration;
import com.company.procedure.Severity;
import com.company.procedure.checkup.Checkup;
import com.company.procedure.medicalprocedure.MedicalProcedure;
import com.company.procedure.medicalprocedure.MedicalProcedureRepository;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SurgeryRepository {
    private static SurgeryRepository instance = null;

    private SurgeryRepository(){
    }

    public static SurgeryRepository getInstance()
    {
        if (instance == null)
        {
            instance = new SurgeryRepository();
        }
        return instance;
    }

    public void createTable() {
        String command = "create table if not exists  surgeries(\n" +
                "\tid int primary key,\n" +
                "\ttype varchar(100),\n" +
                "\trisk enum( 'LOW','MEDIUM','HIGH','INSIGNIFICANT'),\n" +
                "    complications varchar(100),\n" +
                "\tCONSTRAINT FK_surgeries_procedures FOREIGN KEY (id)\n" +
                "    REFERENCES medicalProcedures(id) ON DELETE CASCADE\n" +
                ");";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Surgery surgery)
    {
        String command = "insert into surgeries(id, type, risk, complications) values (?, ?, ?, ?);";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, surgery.getId());
            preparedStatement.setString(2, surgery.getType());
            preparedStatement.setString(3, surgery.getRisk().name());
            if (surgery.getComplications() == null)
                preparedStatement.setNull(4, Types.NULL);
            else
                preparedStatement.setString(4, surgery.getComplications());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Surgery surgery)
    {
        String command = "update surgeries set type = ?, risk = ?, complications = ? where id = ?;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setString(1, surgery.getType());
            preparedStatement.setString(2, surgery.getRisk().name());
            preparedStatement.setString(3, surgery.getComplications());
            preparedStatement.setInt(4, surgery.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Surgery> selectAll()
    {
        String command = "select * from surgeries;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(command);

            return mapToSurgeries(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Surgery> mapToSurgeries(ResultSet resultSet) throws SQLException {
        List<Surgery> result = new ArrayList<>();
        while (resultSet.next()) {
            MedicalProcedure correspondingProcedure = MedicalProcedureRepository.getInstance().getProcedureById(resultSet.getInt(1));
            result.add(new Surgery(resultSet.getInt(1),correspondingProcedure.getAppointmentId(), correspondingProcedure.getStartTime(),
                    correspondingProcedure.getDuration(),resultSet.getString(2),
                    Severity.valueOf(resultSet.getString(3)), resultSet.getString(4)));
        }

        return result;
    }

}
