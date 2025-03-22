package com.aviato;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/pages/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set up the stage
        primaryStage.setTitle("Admin Panel Dashboard");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600.0);
        primaryStage.setMinHeight(400.0);
        primaryStage.setResizable(true);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}