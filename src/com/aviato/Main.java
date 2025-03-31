package com.aviato;

import com.aviato.Types.Customer;
import com.aviato.Types.Pages;
import com.aviato.db.dao.Customer_dao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
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
        } catch (Exception e) {
            System.err.println("Failed to load Maven Pro font: " + e.getMessage());
        }

        try{
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            double width = visualBounds.getWidth();
            double height = visualBounds.getHeight();
            Pages.SetWidthHeightOfScreen(width, height);
            Pages.LoadAllPages();

            currentStage = primaryStage;
            primaryStage.setTitle("Admin Panel Dashboard");
            primaryStage.setScene(Pages.GetPaymentsScene());

            primaryStage.setX(visualBounds.getMinX());
            primaryStage.setY(visualBounds.getMinY());
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
            primaryStage.setMaximized(true);
            primaryStage.show();


            System.out.println("Stage - Width: " + primaryStage.getWidth()+", "+ width + ", Height: " + primaryStage.getHeight()+", "+height);
            System.out.println("Stage - X: " + primaryStage.getX() +", "+ visualBounds.getMinX() + ", Y: " + primaryStage.getY() + ", "+visualBounds.getMinY());

            Customer cust = Customer_dao.GetCustomer(41l);
            System.out.println(cust.getName());
        }
        catch (Exception ex){
            System.out.println("MainErr: "+ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}