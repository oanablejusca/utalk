package com.utalk.services;

import com.utalk.model.User;
import com.utalk.repository.DatabaseConnection;
import com.utalk.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getById(Integer profileId) {
        System.out.println("Called UserService getById");
        try (Connection connection = DatabaseConnection.getConnection()) {
            return userRepository.getById(connection, profileId);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }

    public List<User> getAll() {
        System.out.println("Called UserService getAll");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return userRepository.getAll(connection);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }

    public User create(User user) {
        System.out.println("Called UserService create");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return userRepository.create(connection, user);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }

    public User update(User user) {
        System.out.println("Called UserService update");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (!userRepository.update(connection, user)) {
                System.out.println("User not found " + user);
            }
            return user;
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }


    public boolean delete(Integer profileId) {
        System.out.println("Called UserService delete");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (userRepository.delete(connection, profileId)) {
                return true;
            }

            System.out.println("Couldn't find user with profile id: " + profileId);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
        }
        return false;
    }
}
