package com.utalk.configuration;

import com.github.javafaker.Faker;
import com.utalk.model.Friendship;
import com.utalk.model.Post;
import com.utalk.model.Profile;
import com.utalk.model.User;
import com.utalk.repository.DatabaseConnection;
import com.utalk.repository.friendship.FriendshipRepository;
import com.utalk.repository.posts.PostsRepository;
import com.utalk.repository.profile.ProfileRepository;
import com.utalk.repository.user.UserRepository;
import javafx.util.Pair;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DatabaseGenerator {
    private static Timestamp timestamp;
    private static Faker faker = new Faker();
    private static Random rand = new Random();


    private static List<Profile> profiles = new ArrayList<>();
    private static ProfileRepository profileRepository = new ProfileRepository();

    private static List<Post> posts = new ArrayList<>();
    private static PostsRepository postsRepository = new PostsRepository();
    private static List<User> users = new ArrayList<>();
    private static UserRepository userRepository = new UserRepository();

    private static List<Pair<Friendship, Friendship>> friendships = new ArrayList<>();
    private static FriendshipRepository friendshipRepository = new FriendshipRepository();


    public DatabaseGenerator() {
    }


    public static Profile generateProfile() {
        Profile profile = new Profile();
        Random random = new Random();
        int noOfRandomPhoto = random.nextInt(6) + 1;
        profile.setPhoto("profile" + noOfRandomPhoto + ".jpg");
        profile.setOccupation(faker.lorem().sentence(1).split(" ", 2)[0]);
        profile.setName(faker.lorem().sentence(2).split(" ", 3)[0] + " " + faker.lorem().sentence(2).split(" ", 3)[1]);
        timestamp = new Timestamp(System.currentTimeMillis());
        profile.setBirthdate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        profile.setOccupation(faker.lorem().sentence(1).split(" ", 2)[0]);
        profile.setLocation(faker.lorem().sentence(1).split(" ", 2)[0]);
        return profile;
    }


    public static Post generatePost() {
        Post post = new Post();
        post.setProfile_id(profiles.get(rand.nextInt(profiles.size())).getId());
        post.setPosted_on(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        post.setContent(faker.lorem().sentence(10));
        return post;
    }

    public static Pair<Friendship, Friendship> generateFriendship() {
        Pair<Friendship, Friendship> p;
        Friendship friendship = new Friendship();
        Friendship friendship1 = new Friendship();

        int a = 0, b = 0;
        int ok = 0;

        while (a == b) {
            friendship.setUser_id1(a = profiles.get(rand.nextInt(profiles.size())).getId());
            friendship.setUser_id2(b = profiles.get(rand.nextInt(profiles.size())).getId());
        }
        friendship1.setUser_id1(b);
        friendship1.setUser_id2(a);

        p = new Pair<>(friendship, friendship1);
        return p;
    }

    public static User generateUser(Integer profile_id) {
        User user = new User();
        String username="";
        String password="";
        while(username.length()<4){
            String firstWord=faker.lorem().word().toLowerCase();
            String secondWord = faker.lorem().word().toLowerCase();
            secondWord = secondWord.substring(0, 1).toUpperCase() + secondWord.substring(1);
            username=firstWord+secondWord;
        }
        user.setUsername(username);

        while(password.length()<4) {
            password = faker.lorem().word().toLowerCase();
        }
        user.setPassword(password);
        user.setProfile_id(profile_id);
        return user;
    }

    public static void generateData(int noOfProfiles, int noOfPosts, int nFriendships) {
        System.out.println("Creating tables and generating Data");

        try (Connection connection = DatabaseConnection.getConnection()) {
            DatabaseConnection.initDatabase(connection);

            userRepository.deleteAll(connection);
            System.out.println("Deleted previous users");

            profileRepository.deleteAll(connection);
            System.out.println("Deleted previous profiles");

            for (int i = 0; i < noOfProfiles; i++) {
                profiles.add(generateProfile());
            }
            for (Profile profile : profiles) {
                profileRepository.create(connection, profile);
                users.add(generateUser(profile.getId()));
                System.out.println("Generated new profile");
            }
            postsRepository.deleteAll(connection);
            System.out.println("Deleted previous posts");

            for (int i = 0; i < noOfPosts; i++) {
                posts.add(generatePost());
            }
            for (Post post : posts) {
                postsRepository.create(connection, post);
                System.out.println("Generated new post");
            }

            friendshipRepository.deleteAll(connection);
            System.out.println("Deleted previous friendships");

            for (int i = 0; i < nFriendships; i++) {
                friendships.add(generateFriendship());
            }
            for (Pair<Friendship, Friendship> friendship : friendships) {
                friendshipRepository.create(connection, friendship.getKey());
                friendshipRepository.create(connection, friendship.getValue());
                System.out.println("Generated new friendship");
            }

            for (User user : users) {
                userRepository.create(connection, user);
                System.out.println("Generated new user");
            }

        } catch (RuntimeException | SQLException exception) {
            System.out.println("Failed to initialise database profiles table with data: " + exception.getMessage());
        }
    }

    static {
        generateData(200, 500, 500);
    }
}