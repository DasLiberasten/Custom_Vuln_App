package com.example.Ingresstokafka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClickHouse {
        private static final Logger logger = LoggerFactory.getLogger(ClickHouse.class);

    public static boolean main(boolean newUser, String username, String password) {
        String url = "jdbc:clickhouse://10.0.2.15:8123/passworddb";
        String usr = "default"; 
        String passwrd = "password"; 

        try (Connection connection = DriverManager.getConnection(url, usr, passwrd)) {
            logger.info("success");
            if (!newUser){
                if (isValidUser(connection, username, password)) {
                    logger.info("User is authenticated");
                    return true;
                } else {
                    logger.info("Invalid credentials");
                    return false;
                }
            } else {
                if (!userExists(connection, username) ) {
                    addUser(connection, "username", "password");
                    logger.info("User created");
                    return true;
                } else {
                    logger.info("User already exists!");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isValidUser(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }
        return false;
    }
    

    private static void addUser(Connection connection, String username, String password) throws SQLException {
            // Пользователь не существует, добавляем его
            String query = "INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
            }
    }
    
    private static boolean userExists(Connection connection, String username) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM users WHERE username = '" + username + "'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        }
        return false;
    }
}