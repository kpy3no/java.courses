package org.java.courses.lesson1.javasql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * view 1lesson_javasql.sql
 */
public class JavaPostgreSql {

    static String url = "jdbc:postgresql://localhost:5432/postgres";
    static String dbUser = "campaigndb";
    static String password = "campaigndb";

    public static void main(String[] args) {

        try {
            List<User> users = getAllUsers();
            System.out.println(users);
            for (User user : users) {
                fillAllComments(user);
            }

            System.out.println(users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            List<User> users = getAllUsersWithComments();
            System.out.println(users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, dbUser, password);
             Statement st = con.createStatement()) {
            ResultSet resultSet = st.executeQuery("select * from users");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setSurname(resultSet.getString(3));
                users.add(user);
            }
        }

        return users;
    }

    public static void fillAllComments(User user) throws Exception {
        List<Comment> comments = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, dbUser, password);
             PreparedStatement st = con.prepareStatement("select * from comment where user_id = ?")) {

            st.setInt(1, user.getId());
            ResultSet resultSet = st.executeQuery();

            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt(1));
                comment.setUserId(resultSet.getInt(2));
                comment.setText(resultSet.getString(3));
                comments.add(comment);
            }

            user.setComments(comments);
        }
    }

    public static List<User> getAllUsersWithComments() throws Exception {
        List<User> users = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, dbUser, password);
             Statement st = con.createStatement()) {
            ResultSet resultSet = st.executeQuery("select * from users inner join comment on comment.user_id = users.id");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setSurname(resultSet.getString(3));

                Comment comment = new Comment();
                comment.setId(resultSet.getInt(4));
                comment.setUserId(resultSet.getInt(5));
                comment.setText(resultSet.getString(6));
                user.setComments(Collections.singletonList(comment));

                users.add(user);
            }
        }

        return users;
    }
}
