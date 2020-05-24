package com.utalk.services;

import com.utalk.model.Friendship;
import com.utalk.repository.DatabaseConnection;
import com.utalk.repository.friendship.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class FriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    public Friendship getById(Integer id) {
        System.out.println("Called FriendshipService getById");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return friendshipRepository.getById(connection, id);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }
    public List<Friendship> getAll() {
        System.out.println("Called FriendshipService getAll");

        try (Connection connection = DatabaseConnection.getConnection()) {
            return friendshipRepository.getAll(connection);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }
    public Friendship create(Friendship friendship) {
        System.out.println("Called FriendshipService create");

        try (Connection connection = DatabaseConnection.getConnection()) {
            friendshipRepository.create(connection, friendship);
            int aux=friendship.getUser_id1();
            friendship.setUser_id1(friendship.getUser_id2());
            friendship.setUser_id2(aux);
            return friendshipRepository.create(connection, friendship);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }
    public Friendship update(Friendship friendship) {
        System.out.println("Called FriendshipService update");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (!friendshipRepository.update(connection, friendship)) {
                System.out.println("Friendship not found " + friendship);
            }
            return friendship;
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
            return null;
        }
    }

    public boolean delete(Integer id) {
        System.out.println("Called FriendshipService delete");

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (friendshipRepository.delete(connection, id)) {
                return true;
            }

            System.out.println("Friendship not found with id: " + id);
        } catch (SQLException exceptionSQL) {
            System.out.println("Database connection could not be established: " + exceptionSQL.getMessage());
        }
        return false;
    }

}
