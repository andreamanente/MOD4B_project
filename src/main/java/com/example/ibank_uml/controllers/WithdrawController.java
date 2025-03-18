package com.example.ibank_uml.controllers;

import com.example.ibank_uml.models.UserDatabase;
import com.example.ibank_uml.models.UserProfile;
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

public class WithdrawController {
    @FXML private TextField amountField;
    @FXML private Label errorLabel;
    @FXML private Label successLabel;

    private String cardNumber;
    private UserProfile user;

    /**
     * Set the card number for this withdrawal session
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
    private void handleWithdraw(ActionEvent event) {
        clearMessages();

        if (cardNumber == null || cardNumber.isEmpty()) {
            showError("Error: Card number not set!");
            return;
        }

        String amountString = amountField.getText().trim();

        try {
            double amount = Double.parseDouble(amountString);

            if (amount <= 0) {
                showError("Invalid amount. Enter a positive value.");
                return;
            }

            if (!hasSufficientBalance(amount)) {
                showError("Insufficient funds.");
                return;
            }

            if (processWithdrawal(amount)) {
                showSuccess("Withdrawal successful! $" + String.format("%.2f", amount) + " has been withdrawn.");
                amountField.clear();

                // Use PauseTransition instead of Thread.sleep
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> navigateToMainMenu(event));
                pause.play();

            } else {
                showError("Error processing withdrawal. Try again.");
            }
        } catch (NumberFormatException e) {
            showError("Invalid input. Please enter a numeric value.");
        }
    }

    private boolean hasSufficientBalance(double amount) {
        if (user == null) {
            user = UserDatabase.getUser(cardNumber);
        }

        if (user != null) {
            return user.getBalance() >= amount;
        }
        return false;
    }

    private boolean processWithdrawal(double amount) {
        if (user == null) {
            user = UserDatabase.getUser(cardNumber);
        }

        if (user != null && user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
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
