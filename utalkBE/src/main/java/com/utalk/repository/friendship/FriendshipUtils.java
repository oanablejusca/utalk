package com.utalk.repository.friendship;

import com.utalk.model.Friendship;

import java.sql.*;

import static com.utalk.model.ModelUtils.checkNullFields;
import static com.utalk.model.ModelUtils.checkNullParameters;
public class FriendshipUtils {

    public Friendship getElementSelected(Connection connection, PreparedStatement statement) {
        checkNullParameters(connection, statement);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return buildElementFromResultSet(connection, resultSet);
            }
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get friendship: " + exceptionSQL.getMessage());
        }
        System.out.println("Post Not Found");
        return null;
    }

    public Friendship buildElementFromResultSet(Connection connection, ResultSet resultSet) throws SQLException {
        checkNullParameters(connection, resultSet);
        Friendship friendship = new Friendship();
        int paramIndex = 0;
        friendship.setId(resultSet.getInt(++paramIndex));
        friendship.setUser_id1(resultSet.getInt(++paramIndex));
        friendship.setUser_id2(resultSet.getInt(++paramIndex));

        return friendship;
    }

    public int prepareElementStatementUpdate(Connection connection, PreparedStatement statement, Friendship friendship) throws SQLException {
        checkNullFields(friendship);
        checkNullParameters(connection, statement);
        int paramIndex = 0;
        statement.setInt(++paramIndex, friendship.getUser_id1());
        statement.setInt(++paramIndex, friendship.getUser_id2());

        return  paramIndex;

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
