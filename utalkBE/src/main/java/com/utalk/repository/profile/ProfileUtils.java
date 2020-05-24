package com.utalk.repository.profile;

import com.utalk.model.Profile;

import java.sql.*;


import static com.utalk.model.ModelUtils.checkNullFields;
import static com.utalk.model.ModelUtils.checkNullParameters;

public class ProfileUtils {

    public Profile getElementSelected(Connection connection, PreparedStatement statement) {
        checkNullParameters(connection, statement);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return buildElementFromResultSet(connection, resultSet);
            }
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get profile: " + exceptionSQL.getMessage());
        }
        System.out.println("Profile Not Found");
        return null;
    }

    public Profile buildElementFromResultSet(Connection connection, ResultSet resultSet) throws SQLException {
        checkNullParameters(connection, resultSet);
        Profile profile = new Profile();
        int paramIndex = 0;

        profile.setId(resultSet.getInt(++paramIndex));
        profile.setName(resultSet.getString(++paramIndex));
        profile.setPhoto(resultSet.getString(++paramIndex));
        profile.setOccupation(resultSet.getString(++paramIndex));
        profile.setBirthdate(resultSet.getDate(++paramIndex).toLocalDate());
        profile.setLocation(resultSet.getString(++paramIndex));
        return profile;
    }

    public int prepareElementStatementUpdate(Connection connection, PreparedStatement statement, Profile profile) throws SQLException {
        checkNullFields(profile);
        checkNullParameters(connection, statement);
        int paramIndex = 0;

        statement.setString(++paramIndex, profile.getName());
        statement.setString(++paramIndex, profile.getPhoto());
        statement.setString(++paramIndex, profile.getOccupation());
        statement.setDate(++paramIndex, Date.valueOf(profile.getBirthdate()));
        statement.setString(++paramIndex, profile.getLocation());
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
