package com.utalk.services;


import com.utalk.model.Profile;
import com.utalk.repository.DatabaseConnection;
import com.utalk.repository.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile getById(Integer id) {
        System.out.println("Called ProfileService getById");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return profileRepository.getById(connection, id);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }

    public List<Profile> getAll() {
        System.out.println("Called ProfileService getAll");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return profileRepository.getAll(connection);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }

    public Profile create(Profile profile) {
        System.out.println("Called ProfileService create");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return profileRepository.create(connection, profile);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }

    public Profile update(Profile profile) {
        System.out.println("Called ProfileService update");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (!profileRepository.update(connection, profile)) {
                System.out.println("Profile not found " + profile);
            }
            return profile;
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }


    public boolean delete(Integer id) {
        System.out.println("Called ProfileService delete");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (profileRepository.delete(connection, id)) {
                return true;
            }

            System.out.println("Profile not found with id: " + id);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
        }
        return false;
    }
}
