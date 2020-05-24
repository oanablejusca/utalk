package com.utalk.services;

import com.utalk.model.Post;
import com.utalk.repository.DatabaseConnection;
import com.utalk.repository.posts.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postsRepository;

    public Post getById(Integer id) {
        System.out.println("Called PostsService getById");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return postsRepository.getById(connection, id);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }
    public List<Post> getAll() {
        System.out.println("Called PostsService getAll");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return postsRepository.getAll(connection);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }
    public Post create(Post post) {
        System.out.println("Called PostService create");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return postsRepository.create(connection, post);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }
    public Post update(Post post) {
        System.out.println("Called PostService update");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (!postsRepository.update(connection, post)) {
                System.out.println("Post not found " + post);
            }
            return post;
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }

    public boolean delete(Integer id) {
        System.out.println("Called PostService delete");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (postsRepository.delete(connection, id)) {
                return true;
            }

            System.out.println("Post not found with id: " + id);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
        }
        return false;
    }

}
