package com.utalk.repository.user;

import com.utalk.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.utalk.model.ModelUtils.checkNullFields;
import static com.utalk.model.ModelUtils.checkNullParameters;

public class UserUtils {



    public User getElementSelected(Connection connection, PreparedStatement statement) {
        checkNullParameters(connection, statement);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return buildElementFromResultSet(connection, resultSet);
            }
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get user: " + exceptionSQL.getMessage());
        }
        System.out.println("User Not Found");
        return null;
    }

    public User buildElementFromResultSet(Connection connection, ResultSet resultSet) throws SQLException {
        checkNullParameters(connection, resultSet);
        User user = new User();
        int paramIndex = 0;

        user.setUsername(resultSet.getString(++paramIndex));
        user.setPassword(resultSet.getString(++paramIndex));
        user.setProfile_id(resultSet.getInt(++paramIndex));
        return user;
    }
    public int prepareElementStatementCreate(Connection connection, PreparedStatement statement, User user) throws SQLException {
        checkNullFields(user);
        checkNullParameters(connection, statement);
        int paramIndex = 0;
        statement.setString(++paramIndex, user.getUsername());
        statement.setString(++paramIndex, user.getPassword());
        statement.setInt(++paramIndex, user.getProfile_id());
        return paramIndex;
    }

    public int prepareElementStatementUpdate(Connection connection, PreparedStatement statement, User user) throws SQLException {
        checkNullFields(user);
        checkNullParameters(connection, statement);
        int paramIndex = 0;

        statement.setString(++paramIndex, user.getPassword());
        statement.setString(++paramIndex, user.getUsername());
        return paramIndex;
    }


    Integer getElementGeneratedId(PreparedStatement statement) {
        checkNullParameters(statement);
        final int GENERATED_KEY_INDEX = 1;

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return Integer.valueOf(generatedKeys.getInt(GENERATED_KEY_INDEX));
            }

            System.out.println("Id not generated after create sql command");
            return null;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get id generated: " + exceptionSQL.getMessage());
            return null;
        }
    }
}
