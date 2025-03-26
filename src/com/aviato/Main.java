package com.aviato;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the Maven Pro font
        try {
            Font regularFont = Font.loadFont(getClass().getResourceAsStream("/fonts/MavenPro-Regular.ttf"), 14);
            Font mediumFont = Font.loadFont(getClass().getResourceAsStream("/fonts/MavenPro-Medium.ttf"), 14);
            Font boldFont = Font.loadFont(getClass().getResourceAsStream("/fonts/MavenPro-Bold.ttf"), 14);
            System.out.println("Maven Pro font loaded successfully.");
            // Print the font family names
            System.out.println("Regular Font Family: " + regularFont.getFamily());
            System.out.println("Medium Font Family: " + mediumFont.getFamily());
            System.out.println("Bold Font Family: " + boldFont.getFamily());
        } catch (Exception e) {
            System.err.println("Failed to load Maven Pro font: " + e.getMessage());
        }

        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/pages/Customer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set up the stage
        primaryStage.setTitle("Admin Panel Dashboard");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600.0);
        primaryStage.setMinHeight(400.0);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}