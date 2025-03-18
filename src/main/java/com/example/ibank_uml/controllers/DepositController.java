package com.example.ibank_uml.controllers;

import com.example.ibank_uml.models.UserDatabase;
import com.example.ibank_uml.models.UserProfile;
import com.example.ibank_uml.utils.ATMUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.IOException;

public class DepositController {
    @FXML
    private TextField amountField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label successLabel;

    private String cardNumber;
    private UserProfile user;

    /**
     * Set the card number for this session
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        // Try to load the user profile
        this.user = UserDatabase.getUser(cardNumber);
    }

    /**
     * Set the user profile directly
     */
    public void setUserProfile(UserProfile user) {
        this.user = user;
        this.cardNumber = user.getCardNumber();
    }

    @FXML
    private void handleDeposit(ActionEvent event) {
        clearMessages();

        if (cardNumber == null || cardNumber.isEmpty()) {
            showError("Error: Card number not set!");
            return;
        }

        String amountString = amountField.getText().trim();

        try {
            double amount = Double.parseDouble(amountString);

            // Validate the deposit amount
            if (!ATMUtils.isValidDepositAmount(String.valueOf(amount))) {
                showError("Invalid deposit amount. Amount must be positive.");
                return;
            }

            // Utilisez UserDatabase au lieu de la classe Deposit
            if (processDeposit(amount)) {
                showSuccess("Deposit successful! $" + String.format("%.2f", amount) + " added to your account.");
                amountField.clear();

                // Use PauseTransition instead of Thread.sleep
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> navigateToMainMenu(event));
                pause.play();

            } else {
                showError("Deposit failed. Please try again.");
            }
        } catch (NumberFormatException e) {
            showError("Invalid input. Please enter a numeric value.");
        }
    }

    /**
     * Process deposit using UserDatabase
     */
    private boolean processDeposit(double amount) {
        if (user == null) {
            user = UserDatabase.getUser(cardNumber);
        }

        if (user != null) {
            double currentBalance = user.getBalance();
            user.setBalance(currentBalance + amount);
            return true;
        }
        return false;
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) {
        navigateToMainMenu(event);
    }

    private void navigateToMainMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ibank_uml/views/fxml/ATMMainMenu.fxml"));
            Parent root = loader.load();

            // Pass user data to main menu controller
            ATMMainMenuController mainMenuController = loader.getController();
            if (user != null) {
                mainMenuController.setUserProfile(user);
            } else if (cardNumber != null) {
                mainMenuController.setCardNumber(cardNumber);
            }

            // Set the scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ATM Main Menu");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error returning to main menu: " + e.getMessage());
        }
    }

    private void clearMessages() {
        errorLabel.setText("");
        if (successLabel != null) {
            successLabel.setText("");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
    }

    private void showSuccess(String message) {
        if (successLabel != null) {
            successLabel.setText(message);
        } else {
            // If no success label exists, use error label with different style
            errorLabel.setStyle("-fx-text-fill: green;");
            errorLabel.setText(message);
        }
    }
}
