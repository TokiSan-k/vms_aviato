package com.aviato;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/pages/Home.fxml")));

        // Create the Scene
        Scene scene = new Scene(root);

        // Apply CSS
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/home.css")).toExternalForm());

        // Set Stage
        primaryStage.setTitle("Home Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}