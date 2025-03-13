package com.aviato;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Debug: Check if the FXML resource is found
        System.out.println("Resource URL: " + Main.class.getResource("/pages/Admin.fxml"));
        if (Main.class.getResource("/pages/Admin.fxml") == null) {
            throw new IOException("FXML file not found at /pages/Admin.fxml. Please check the file location in src/main/resources/pages/");
        }

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/pages/Admin.fxml"));
        Parent root = loader.load(); // Load the root node (VBox) from the FXML
        Scene scene = new Scene(root, 861, 643); // Create a Scene with the loaded Parent

        primaryStage.setTitle("Admin Panel Dashboard");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}