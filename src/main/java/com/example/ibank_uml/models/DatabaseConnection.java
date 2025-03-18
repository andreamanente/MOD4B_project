package com.example.ibank_uml.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/ibank";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        try {
            // Class.forName("com.mysql.cj.jdbc.Driver"); // Optional for newer versions of JDBC
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Log the exception or handle more gracefully
            e.printStackTrace();
            throw new SQLException("Error while connecting to the database: " + e.getMessage(), e);
        }
    }

    // Method to update balance
    public static boolean updateBalance(String cardNumber, double amount) {
        String query = "UPDATE accounts SET balance = balance + ? WHERE card_number = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, cardNumber);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;  // If rows are affected, the update was successful

        } catch (SQLException e) {
            // Log the exception or handle more gracefully
            e.printStackTrace();
            return false;  // Return false if an error occurs
        }
    }
}
