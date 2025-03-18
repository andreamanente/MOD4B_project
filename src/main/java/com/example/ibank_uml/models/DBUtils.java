package com.example.ibank_uml.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    // Database connection parameters (modify according to your database)
    private static final String URL = "jdbc:mysql://localhost:3306/ibank";  // Modify this URL as needed
    private static final String USER = "root";  // Your database username
    private static final String PASSWORD = "password";  // Your database password

    // Load the database driver (Optional for JDBC 4.0 and newer)
    static {
        try {
            // JDBC 4.0 and later automatically loads the driver, but it's safe to leave this in for compatibility
            // Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("MySQL JDBC Driver not found.");
        }
    }

    // Method to get the database connection
    public static Connection getConnection() throws SQLException {
        try {
            // Return the database connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Log the exception or handle it more gracefully
            e.printStackTrace();
            throw new SQLException("Error while connecting to the database: " + e.getMessage(), e);
        }
    }

    // Close the connection (for safe resource management)
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            // Log or handle exception properly
            e.printStackTrace();
        }
    }
}
