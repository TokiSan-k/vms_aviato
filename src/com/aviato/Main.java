package com.aviato;

import com.aviato.Types.Customer;
import com.aviato.Types.Pages;
import com.aviato.db.dao.Customer_dao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;

public class Main extends Application {

    public static Stage currentStage = null;

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

        // Load All FXML file
        try{
            Pages.LoadAllPages();

            // Set up the stage
            currentStage = primaryStage;
            primaryStage.setTitle("Admin Panel Dashboard");
            primaryStage.setScene(Pages.GetCustomerScene());
            primaryStage.setMinWidth(600.0);
            primaryStage.setMinHeight(400.0);
            primaryStage.setMaximized(true);
            primaryStage.show();

            Customer cust = Customer_dao.GetCustomer(41l);
            System.out.println(cust.getName());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}