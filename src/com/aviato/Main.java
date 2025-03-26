package com.aviato;

import com.aviato.db.dao.*;
import com.aviato.Types.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
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


        try
        {
            Customer cust = Customer_dao.GetCustomer(1l);
            System.out.println(cust.getName());
            System.out.println(cust.getEmailId());
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}