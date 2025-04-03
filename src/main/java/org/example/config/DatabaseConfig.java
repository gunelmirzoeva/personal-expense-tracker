package org.example.config;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String URL = "jdbc:postgresql://localhost:5433/expense_tracker_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "gunel1969";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected successfully" + conn);
        } catch (SQLException e) {
            System.out.println("Connection failed" + e.getMessage());
        }
        return conn;
    }
}
