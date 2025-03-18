package com.example.ibank_uml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ATMLogin extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ibank_uml/views/fxml/ATMLogin.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("ATM Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading FXML: /fxml/ATMLogin.fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
