package com.example.ibank_uml;

import com.example.ibank_uml.controllers.ATMMainMenuController;
import com.example.ibank_uml.controllers.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ATMApplication extends Application {

    private SceneController sceneController; // Keep a reference

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Create the SceneController once
        this.sceneController = new SceneController(primaryStage);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/ibank_uml/views/fxml/ATMMainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

        // Pass the sceneController to the ATMMainMenuController
        ATMMainMenuController controller = fxmlLoader.getController();

        // Check if controller implements SceneControllerAware
        if (controller instanceof com.example.ibank_uml.controllers.SceneControllerAware) {
            ((com.example.ibank_uml.controllers.SceneControllerAware) controller).setSceneController(this.sceneController);
        }

        primaryStage.setTitle("ATM Machine");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
