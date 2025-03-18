package com.example.ibank_uml.controllers;

import com.example.ibank_uml.models.UserDatabase;
import com.example.ibank_uml.models.UserProfile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class BalanceController {
    @FXML
    private Label balanceLabel;

    private UserProfile loggedInUser;
    private String cardNumber;

    /**
     * Set the user profile information
     */
    public void setUserProfile(UserProfile user) {
        this.loggedInUser = user;
        this.cardNumber = user.getCardNumber();
        updateBalanceDisplay();
    }

    /**
     * This method must be called before displaying the balance screen if UserProfile isn't available.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        // Try to load user from database
        this.loggedInUser = UserDatabase.getUser(cardNumber);
        displayBalance();
    }

    /**
     * Updates the balance display with current user info
     */
    private void updateBalanceDisplay() {
        if (loggedInUser != null) {
            double currentBalance = loggedInUser.getBalance();
            balanceLabel.setText(String.format("Balance: $%.2f", currentBalance));
        } else {
            balanceLabel.setText("Error: No user found.");
        }
    }

    /**
     * Retrieves and displays the user's balance.
     */
    private void displayBalance() {
        if (cardNumber == null || cardNumber.isEmpty()) {
            balanceLabel.setText("Error: Card number not provided.");
            return;
        }

        if (loggedInUser == null) {
            loggedInUser = UserDatabase.getUser(cardNumber);
        }

        if (loggedInUser != null) {
            double balance = loggedInUser.getBalance();
            balanceLabel.setText(String.format("Balance: $%.2f", balance));
        } else {
            balanceLabel.setText("Error: User not found.");
        }
    }

    @FXML
    private void handleBackToMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ibank_uml/views/fxml/ATMMainMenu.fxml"));
            Parent root = loader.load();

            // Pass user data to main menu controller
            ATMMainMenuController mainMenuController = loader.getController();
            if (loggedInUser != null) {
                mainMenuController.setUserProfile(loggedInUser);
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
            balanceLabel.setText("Error returning to main menu: " + e.getMessage());
        }
    }
}
