package com.company.user.user;

import com.company.database.DatabaseConfiguration;
import com.company.procedure.treatment.TreatmentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository instance = null;

    private UserRepository(){
    }

    public static UserRepository getInstance()
    {
        if (instance == null)
        {
            instance = new UserRepository();
        }
        return instance;
    }

    public void createTable() {
        String command = "create table if not exists users(\n" +
                "\tid int primary key auto_increment,\n" +
                "    username varchar(100) not null,\n" +
                "    email varchar(100) not null,\n" +
                "    password varchar(5000) not null\n" +
                ");\n";
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(User user)
    {
        String command = "insert into users(id, username, email, password)\n" +
                "values (?, ?, ?, ?);";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int id)
    {
        String command = "select * from users where id = ?;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return mapToUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> selectAll()
    {
        String command = "select * from users;";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(command);

            return mapToUsers(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private User mapToUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4));
        }

        return null;
    }

    private List<User> mapToUsers(ResultSet resultSet) throws SQLException {
        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4)));
        }

        return result;
    }

    public void delete(int id)
    {
        String command = "delete from users where id = ?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(command)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
