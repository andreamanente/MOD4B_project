package com.example.ibank_uml.utils;

public class ATMUtils {

    // Validates if a given string is a valid card number (e.g., length check, numeric check)
    public static boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }
        return cardNumber.matches("\\d+"); // Only digits allowed
    }

    // Validates if a given PIN is a valid format (e.g., 4-digit PIN)
    public static boolean isValidPin(String pin) {
        if (pin == null || pin.length() != 4) {
            return false;
        }
        return pin.matches("\\d+"); // Only digits allowed
    }

    // Format a double amount to a string with two decimal places for display (e.g., for balance)
    public static String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }

    // Format the error message in a standardized way for UI display
    public static String formatErrorMessage(String errorMessage) {
        return "Error: " + errorMessage;
    }

    // Utility to check if a string is a valid deposit amount (positive double)
    public static boolean isValidDepositAmount(String amountString) {
        try {
            double amount = Double.parseDouble(amountString);
            return amount > 0;
        } catch (NumberFormatException e) {
            return false; // If the input is not a valid number, return false
        }
    }
}
