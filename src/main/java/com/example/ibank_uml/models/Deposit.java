package com.example.ibank_uml.models;

public class Deposit {
    private double amount;

    // Constructor
    public Deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    // Process deposit and update balance in database
    public boolean processDeposit(String cardNumber) {
        try {
            boolean success = DatabaseConnection.updateBalance(cardNumber, amount);
            if (!success) {
                // Optionally log or notify failure
                System.err.println("Failed to process deposit for card number: " + cardNumber);
            }
            return success;
        } catch (Exception e) {
            e.printStackTrace(); // Or use logging
            // Optionally rethrow or return false if deposit fails
            return false;
        }
    }
}
