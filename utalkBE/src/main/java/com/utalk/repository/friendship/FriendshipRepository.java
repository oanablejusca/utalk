package com.utalk.repository.friendship;

import com.utalk.model.Friendship;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.utalk.repository.CrudRepository;

import static com.utalk.model.ModelUtils.checkNullFields;
import static com.utalk.model.ModelUtils.checkNullParameters;

@Repository
public class FriendshipRepository implements CrudRepository<Friendship> {

    private static final FriendshipUtils repositoryUtils = new FriendshipUtils();

    @Override
    public Friendship create(Connection connection, Friendship friendship) {
        checkNullParameters(connection);
        checkNullFields(friendship);
        final String insertSql = "INSERT INTO " +
                "FRIENDSHIP(USER_ID1, USER_ID2)" +
                "VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSql, new String[]{"ID"})) {
            repositoryUtils.prepareElementStatementUpdate(connection, statement, friendship);
            statement.executeUpdate();
            friendship.setId(repositoryUtils.getElementGeneratedId(statement));
            return friendship;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to insert friendship: " + exceptionSQL.getMessage());
            return null;
        }
    }

    @Override
    public Friendship getById(Connection connection, Integer id) {
        checkNullParameters(connection, id);
        final String selectSql = "SELECT * FROM FRIENDSHIP WHERE ID = ?";
        int paramIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
            statement.setInt(++paramIndex, id);

            return repositoryUtils.getElementSelected(connection, statement);
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get friendship: " + exceptionSQL.getMessage());
            return null;
        }
    }


    @Override
    public List<Friendship> getAll(Connection connection) {
        checkNullParameters(connection);

        final String selectSql = "SELECT * FROM FRIENDSHIP";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(selectSql)) {
            List<Friendship> posts = new ArrayList<>();

            while (resultSet.next()) {
                posts.add(repositoryUtils.buildElementFromResultSet(connection, resultSet));
            }
            return posts;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get friendships: " + exceptionSQL.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Connection connection, Friendship friendship) {
        checkNullFields(friendship);
        checkNullParameters(connection, friendship.getId());
        final String updateSql = "UPDATE FRIENDSHIP SET USER_ID1 = ?, USER_ID2 = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
            int paramIndex = repositoryUtils.prepareElementStatementUpdate(connection, statement, friendship);
            statement.setInt(++paramIndex, friendship.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to update friendship: " + exceptionSQL.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Connection connection, Integer id) {
        checkNullParameters(connection, id);
        final String deleteSql = "DELETE FROM FRIENDSHIP WHERE ID = ?";
        int paramIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            statement.setInt(++paramIndex, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to delete friendship: " + exceptionSQL.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAll(Connection connection) {
        checkNullParameters(connection);
        final String deleteSql = "DELETE FROM FRIENDSHIP";

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            return statement.executeUpdate() > 0 && getAll(connection).isEmpty();
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to delete friendships: " + exceptionSQL.getMessage());
            return false;
        }

    }
}
