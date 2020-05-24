package com.utalk.repository.profile;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.utalk.model.Profile;
import com.utalk.repository.CrudRepository;

import static com.utalk.model.ModelUtils.checkNullFields;
import static com.utalk.model.ModelUtils.checkNullParameters;

@Repository
public class ProfileRepository implements CrudRepository<Profile> {

    private static final ProfileUtils repositoryUtils = new ProfileUtils();

    @Override
    public Profile create(Connection connection, Profile profile) {
        checkNullParameters(connection);
        checkNullFields(profile);
        final String insertSql = "INSERT INTO " +
                "PROFILES(NAME, PHOTO, OCCUPATION, BIRTHDATE, LOCATION) " +
                "VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSql, new String[]{"ID"})) {
            repositoryUtils.prepareElementStatementUpdate(connection, statement, profile);
            statement.executeUpdate();

            profile.setId(repositoryUtils.getElementGeneratedId(statement));
            return profile;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to insert profile: " + exceptionSQL.getMessage());
            return null;
        }
    }

    @Override
    public Profile getById(Connection connection, Integer id) {
        checkNullParameters(connection, id);
        final String selectSql = "SELECT * FROM PROFILES WHERE ID = ?";
        int paramIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
            statement.setInt(++paramIndex, id);

            return repositoryUtils.getElementSelected(connection, statement);
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get profile: " + exceptionSQL.getMessage());
            return null;
        }
    }

    @Override
    public List<Profile> getAll(Connection connection) {
        checkNullParameters(connection);

        final String selectSql = "SELECT * FROM PROFILES";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(selectSql)) {
            List<Profile> questions = new ArrayList<>();

            while (resultSet.next()) {
                questions.add(repositoryUtils.buildElementFromResultSet(connection, resultSet));
            }
            return questions;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get profiles: " + exceptionSQL.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Connection connection, Profile profile) {
        checkNullFields(profile);
        checkNullParameters(connection, profile.getId());
        final String updateSql = "UPDATE PROFILES SET NAME = ?, PHOTO = ?, OCCUPATION = ?, BIRTHDATE = ?, LOCATION = ? WHERE ID = ?";


        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
            int paramIndex = repositoryUtils.prepareElementStatementUpdate(connection, statement, profile);
            statement.setInt(++paramIndex, profile.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to update profile: " + exceptionSQL.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Connection connection, Integer id) {
        checkNullParameters(connection, id);
        final String deleteSql = "DELETE FROM PROFILES WHERE ID = ?";
        int paramIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            statement.setInt(++paramIndex, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to delete profile: " + exceptionSQL.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAll(Connection connection) {
        checkNullParameters(connection);
        final String deleteSql = "DELETE FROM PROFILES";

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            return statement.executeUpdate() > 0 && getAll(connection).isEmpty();
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to delete profiles: " + exceptionSQL.getMessage());
            return false;
        }
    }
}



