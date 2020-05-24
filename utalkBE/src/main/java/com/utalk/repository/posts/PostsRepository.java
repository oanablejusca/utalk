package com.utalk.repository.posts;

import com.utalk.model.Post;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.utalk.repository.CrudRepository;

import static com.utalk.model.ModelUtils.checkNullFields;
import static com.utalk.model.ModelUtils.checkNullParameters;

@Repository
public class PostsRepository implements CrudRepository<Post> {

    private static final PostsUtils repositoryUtils = new PostsUtils();

    @Override
    public Post create(Connection connection, Post post) {
        checkNullParameters(connection);
        checkNullFields(post);
        final String insertSql = "INSERT INTO " +
                "POSTS(PROFILE_ID,POSTED_ON,CONTENT)" +
                "VALUES(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSql, new String[]{"PROFILE_ID"})) {
            repositoryUtils.prepareElementStatementUpdate(connection, statement, post);
            statement.executeUpdate();
            post.setId(repositoryUtils.getElementGeneratedId(statement));
            return post;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to insert post: " + exceptionSQL.getMessage());
            return null;
        }
    }

    @Override
    public Post getById(Connection connection, Integer id) {
        checkNullParameters(connection, id);
        final String selectSql = "SELECT * FROM POSTS WHERE ID = ?";
        int paramIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
            statement.setInt(++paramIndex, id);

            return repositoryUtils.getElementSelected(connection, statement);
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get post: " + exceptionSQL.getMessage());
            return null;
        }
    }


    @Override
    public List<Post> getAll(Connection connection) {
        checkNullParameters(connection);

        final String selectSql = "SELECT * FROM POSTS";

        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(selectSql)) {
            List<Post> posts = new ArrayList<>();

            while (resultSet.next()) {
                posts.add(repositoryUtils.buildElementFromResultSet(connection, resultSet));
            }
            return posts;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to get posts: " + exceptionSQL.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Connection connection, Post post) {
        checkNullFields(post);
        checkNullParameters(connection, post.getId());
        final String updateSql = "UPDATE POSTS SET PROFILE_ID = ?, POSTED_ON = ?, CONTENT = ? WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
            int paramIndex = repositoryUtils.prepareElementStatementUpdate(connection, statement, post);
            statement.setInt(++paramIndex, post.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to update post: " + exceptionSQL.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Connection connection, Integer id) {
        checkNullParameters(connection, id);
        final String deleteSql = "DELETE FROM POSTS WHERE ID = ?";
        int paramIndex = 0;

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            statement.setInt(++paramIndex, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to delete post: " + exceptionSQL.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAll(Connection connection) {
        checkNullParameters(connection);
        final String deleteSql = "DELETE FROM POSTS";

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            return statement.executeUpdate() > 0 && getAll(connection).isEmpty();
        } catch (SQLException exceptionSQL) {
            System.out.println("Failed to delete posts: " + exceptionSQL.getMessage());
            return false;
        }

    }
}
