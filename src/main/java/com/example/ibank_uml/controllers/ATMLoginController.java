package com.example.ibank_uml.controllers;

import com.example.ibank_uml.models.UserDatabase;
import com.example.ibank_uml.models.UserProfile;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.ibank_uml.utils.ATMUtils;
import java.io.IOException;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;

public class ATMLoginController {

    @FXML
    private TextField cardNumberField;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField pinField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String cardNumber = cardNumberField.getText().trim();
        String pin = pinField.getText().trim();

        // Display processing message
        messageLabel.setText("Processing login...");

        // Clear previous errors
        errorLabel.setText("");

        // Validate card number format first
        if (!ATMUtils.isValidCardNumber(cardNumber)) {
            errorLabel.setText("Invalid card number format. Please try again.");
            return;
        }

        // Check user credentials
        UserProfile user = UserDatabase.getUser(cardNumber);

        if (user != null && user.getPin().equals(pin)) {
            // Successful login, navigate to main menu
            navigateToMainMenu(event, user);
        } else {
            // Failed login
            messageLabel.setText("Invalid card number or PIN.");
        }
    }

    private void navigateToMainMenu(ActionEvent event, UserProfile user) {
        try {
            // Load the FXML file with correct path
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ibank_uml/views/fxml/ATMMainMenu.fxml"));
            Parent root = loader.load();

            // Get controller and pass user data
            ATMMainMenuController mainMenuController = loader.getController();
            if (mainMenuController != null) {
                mainMenuController.setUserProfile(user);
                mainMenuController.setCardNumber(user.getCardNumber());
            } else {
                System.out.println("Warning: Main Menu Controller is null!");
            }

            // Set up the new scene
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ATM Main Menu");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading Main Menu: " + e.getMessage());
        }
    }
}
