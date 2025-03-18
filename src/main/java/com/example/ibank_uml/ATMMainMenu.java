package com.example.ibank_uml;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ATMMainMenu extends Application {
    @Override
    public void start(Stage primaryStage) {
        Label welcomeLabel = new Label("Welcome to iBank ATM");
        Button balanceButton = new Button("Check Balance");
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button exitButton = new Button("Exit");

        // Handle Check Balance action
        balanceButton.setOnAction(e -> openBalanceScreen(primaryStage));

        // Handle Deposit action
        depositButton.setOnAction(e -> openDepositScreen(primaryStage));

        // Handle Withdraw action
        withdrawButton.setOnAction(e -> openWithdrawScreen(primaryStage));

        // Handle Exit action with confirmation
        exitButton.setOnAction(e -> handleExit(primaryStage));

        VBox layout = new VBox(10, welcomeLabel, balanceButton, depositButton, withdrawButton, exitButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("ATM Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openBalanceScreen(Stage primaryStage) {
        System.out.println("Balance Inquiry Selected");
        // Code to load the balance screen goes here
    }

    private void openDepositScreen(Stage primaryStage) {
        System.out.println("Deposit Selected");
        // Code to load the deposit screen goes here
    }

    private void openWithdrawScreen(Stage primaryStage) {
        System.out.println("Withdraw Selected");
        // Code to load the withdraw screen goes here
    }

    private void handleExit(Stage primaryStage) {
        Alert exitConfirmation = new Alert(AlertType.CONFIRMATION);
        exitConfirmation.setTitle("Exit Confirmation");
        exitConfirmation.setHeaderText("Are you sure you want to exit?");
        exitConfirmation.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                primaryStage.close();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
