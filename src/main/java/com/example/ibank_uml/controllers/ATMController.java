package com.example.ibank_uml.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ATMController {
    @FXML private Label messageLabel;
    @FXML private TextField pinField;
    @FXML private Button backButton;

    private String cardNumber;

    public void insertCard(String cardNumber) {
        this.cardNumber = cardNumber;
        messageLabel.setText("Please enter your PIN");
    }

    @FXML
    private void handlePinEntry() {
        String enteredPin = pinField.getText();

        if (validatePin(cardNumber, enteredPin)) {
            openTransactionMenu();
        } else {
            messageLabel.setText("Invalid PIN. Try again.");
            pinField.clear(); // Clears the field after an invalid attempt
        }
    }

    private boolean validatePin(String cardNumber, String enteredPin) {
        // Implement PIN validation logic (database check in the future)
        return "1234".equals(enteredPin);
    }

    private void openTransactionMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ATMMainMenu.fxml"));
            Scene scene = new Scene(loader.load());

            // Get controller and pass card number
            ATMMainMenuController controller = loader.getController();
            controller.setCardNumber(cardNumber);

            // Set new stage
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Select Transaction");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ATMMain.fxml"));
            Scene scene = new Scene(loader.load());

            // Get current stage and update scene
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("ATM - Insert Card"); // Ensures title consistency
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
