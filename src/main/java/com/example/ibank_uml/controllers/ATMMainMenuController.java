package com.example.ibank_uml.controllers;

import com.example.ibank_uml.models.UserDatabase;
import com.example.ibank_uml.models.UserProfile;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ATMMainMenuController {
    private UserProfile user;
    private String cardNumber;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button checkBalanceButton;

    @FXML
    private Button depositButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button logoutButton;

    public void setUserProfile(UserProfile user) {
        this.user = user;
        this.cardNumber = user.getCardNumber();
        updateWelcomeMessage();
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        // Try to load the user from database
        this.user = UserDatabase.getUser(cardNumber);
        updateWelcomeMessage();
    }

    private void updateWelcomeMessage() {
        if (welcomeLabel != null) {
            if (user != null) {
                welcomeLabel.setText("Welcome, User #" + user.getCardNumber());
            } else if (cardNumber != null) {
                welcomeLabel.setText("Welcome, User #" + cardNumber);
            } else {
                welcomeLabel.setText("Welcome to ATM");
            }
        }
    }

    @FXML
    private void handleCheckBalance(ActionEvent event) {
        openScreen("/com/example/ibank_uml/views/fxml/Balance.fxml", "Balance", event, BalanceController.class);
    }

    @FXML
    private void handleDeposit(ActionEvent event) {
        openScreen("/com/example/ibank_uml/views/fxml/Deposit.fxml", "Deposit", event, DepositController.class);
    }

    @FXML
    private void handleWithdraw(ActionEvent event) {
        openScreen("/com/example/ibank_uml/views/fxml/Withdraw.fxml", "Withdraw", event, WithdrawController.class);
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ibank_uml/views/fxml/ATMLogin.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ATM Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading login screen: " + e.getMessage());
        }
    }

    private void openScreen(String fxmlPath, String title, ActionEvent event, Class<?> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Pass user data to the controller
            Object controller = loader.getController();

            if (controller instanceof BalanceController) {
                if (user != null) {
                    ((BalanceController) controller).setUserProfile(user);
                } else {
                    ((BalanceController) controller).setCardNumber(cardNumber);
                }
            } else if (controller instanceof DepositController) {
                if (user != null) {
                    ((DepositController) controller).setUserProfile(user);
                } else {
                    ((DepositController) controller).setCardNumber(cardNumber);
                }
            } else if (controller instanceof WithdrawController) {
                if (user != null) {
                    ((WithdrawController) controller).setUserProfile(user);
                } else {
                    ((WithdrawController) controller).setCardNumber(cardNumber);
                }
            }

            // Get the current stage and show new scene
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle(title);
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: " + fxmlPath);
        }
    }
}