package com.utalk.repository.posts;

import com.utalk.model.Post;

import java.sql.*;

import static com.utalk.model.ModelUtils.checkNullFields;
import static com.utalk.model.ModelUtils.checkNullParameters;
public class PostsUtils {

    public Post getElementSelected(Connection connection, PreparedStatement statement) {
        checkNullParameters(connection, statement);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return buildElementFromResultSet(connection, resultSet);
            }
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get post: " + exceptionSQL.getMessage());
        }
        System.out.println("Post Not Found");
        return null;
    }

    public Post buildElementFromResultSet(Connection connection, ResultSet resultSet) throws SQLException {
        checkNullParameters(connection, resultSet);
        Post post = new Post();
        int paramIndex = 0;
        post.setId(resultSet.getInt(++paramIndex));
        post.setProfile_id(resultSet.getInt(++paramIndex));
        post.setPosted_on(resultSet.getDate(++paramIndex).toLocalDate());
        post.setContent(resultSet.getString(++paramIndex));
        return post;
    }

    public int prepareElementStatementUpdate(Connection connection, PreparedStatement statement, Post post) throws SQLException {
        checkNullFields(post);
        checkNullParameters(connection, statement);
        int paramIndex = 0;
        statement.setInt(++paramIndex, post.getProfile_id());
        statement.setDate(++paramIndex,Date.valueOf(post.getPosted_on()));
        statement.setString(++paramIndex, post.getContent());
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
