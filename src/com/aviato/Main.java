package com.aviato;

import com.aviato.Types.*;
import com.aviato.Types.Pages;
import com.aviato.db.dao.Customer_dao;
import com.aviato.db.dao.Employee_dao;
import com.aviato.db.dao.Inventory_dao;
import com.aviato.db.dao.Service_dao;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static Stage currentStage = null;
    List<ServiceItem> items = new ArrayList<ServiceItem>();

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

            Employee emp = Employee_dao.GetEmployee(5l);
            System.out.println("Emp Name: "+emp.getEmpName());
            System.out.println("Emp Email: "+emp.getEmail());

            Item item = Inventory_dao.GetItem(61l);
            System.out.println("Item ID:" +item.getItemId());
            System.out.println("Item Name:" +item.getItemName());
            System.out.println("Item Price:" +item.getPricePerUnit());

            Service srv = Service_dao.GetService(2l);
            System.out.println("Service ID: " +srv.getServiceId());
            System.out.println("Service Type: " +srv.getServiceType());
            System.out.println("Service Cost: " +srv.getCost());
        }
        catch (Exception ex){
            System.out.println("MainErr: "+ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}