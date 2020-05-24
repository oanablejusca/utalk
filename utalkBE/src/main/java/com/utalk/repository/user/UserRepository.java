package com.utalk.repository.user;

import com.utalk.model.User;
import com.utalk.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.utalk.model.ModelUtils.checkNullFields;
import static com.utalk.model.ModelUtils.checkNullParameters;

@Repository
public class UserRepository implements CrudRepository<User> {

    private static final UserUtils repositoryUtils = new UserUtils();

    @Override
    public User create(Connection connection, User user) {
        checkNullParameters(connection);
        checkNullFields(user);
        final String insertSql = "INSERT INTO " +
                "USERS(USERNAME, PASSWORD, PROFILE_ID) " +
                "VALUES(?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
            repositoryUtils.prepareElementStatementCreate(connection, statement, user);
            statement.executeUpdate();
            return user;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to insert user: " + exceptionSQL.getMessage());
            return null;
        }
    }

    @Override
    public User getById(Connection connection, Integer profile_id) {
        checkNullParameters(connection, profile_id);
        final String selectSql = "SELECT * FROM USERS WHERE PROFILE_ID = ?";
        int paramIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
            statement.setInt(++paramIndex, profile_id);

            return repositoryUtils.getElementSelected(connection, statement);
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get this profile's user: " + exceptionSQL.getMessage());
            return null;
        }
    }

    @Override
    public List<User> getAll(Connection connection) {
        checkNullParameters(connection);

        final String selectSql = "SELECT * FROM USERS";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(selectSql)) {
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(repositoryUtils.buildElementFromResultSet(connection, resultSet));
            }
            return users;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get all users: " + exceptionSQL.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Connection connection, User user) {
        checkNullFields(user);
        checkNullParameters(connection, user.getUsername());
        final String updateSql = "UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?";


        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
            int paramIndex = repositoryUtils.prepareElementStatementUpdate(connection, statement, user);
            return statement.executeUpdate() > 0;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to update user's password: " + exceptionSQL.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Connection connection, Integer profile_id) {
        checkNullParameters(connection, profile_id);
        final String deleteSql = "DELETE FROM USERS WHERE PROFILE_ID = ?";
        int paramIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            statement.setInt(++paramIndex, profile_id);

            return statement.executeUpdate() > 0;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to delete profile's user: " + exceptionSQL.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAll(Connection connection) {
        checkNullParameters(connection);
        final String deleteSql = "DELETE FROM USERS";

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            return statement.executeUpdate() > 0 && getAll(connection).isEmpty();
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to delete all users: " + exceptionSQL.getMessage());
            return false;
        }
    }
}
