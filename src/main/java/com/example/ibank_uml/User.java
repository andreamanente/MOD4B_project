package com.example.ibank_uml;

public class User {
    private String name;
    private String cardNumber;
    private String pin;
    private double balance;

    // Constructor with validation
    public User(String name, String cardNumber, String pin, double balance) {
        if (name == null || name.isEmpty() || cardNumber == null || cardNumber.isEmpty() || pin == null || pin.isEmpty()) {
            throw new IllegalArgumentException("Name, card number, and PIN cannot be null or empty.");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
        this.name = name;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean verifyPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    // Direct deposit method, balance is updated
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
    }

    // Withdrawal method, checks if balance is sufficient
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            return false; // Insufficient funds
        }
        this.balance -= amount;
        return true;
    }
}
