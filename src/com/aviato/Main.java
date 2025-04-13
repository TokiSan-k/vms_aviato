package com.aviato;

import com.aviato.Controllers.SideNavBar_Cltr;
import com.aviato.Types.*;
import com.aviato.Types.Pages;
import com.aviato.Utils.AlertBox;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.db.HibernateUtil;
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
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static Stage currentStage = null;
    List<ServiceItem> items = new ArrayList<ServiceItem>();
    private static String roleName;
    public static List<SideNavBar_Cltr> AllsideNavIns = new ArrayList<SideNavBar_Cltr>();

    public static String GetRoleName(){ return roleName;}
    public static void SetRoleName(String RoleName) {
        roleName = RoleName;
    }

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
            HibernateUtil.Init();

            currentStage = primaryStage;
            primaryStage.setTitle("Admin Panel Dashboard");
            primaryStage.setScene(Pages.GetLogInScene());
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
            primaryStage.setResizable(true);
            primaryStage.setMaximized(true);

            primaryStage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                if (newWidth.doubleValue() != width) {
                    System.out.println("Width changed to " + newWidth );
                }
            });

            primaryStage.heightProperty().addListener((obs, oldHeight, newHeight) -> {
                if (newHeight.doubleValue() != height) {
                    System.out.println("Height changed to " + newHeight);
                }
            });

            primaryStage.show();

        }
        catch (Exception ex){
            AlertBox.ShowAlert(Alert.AlertType.WARNING, "Information", "Cannot connect to database check connection! Close Application.");
        }
    }

    @Override
    public void stop() {
        Worker.shutdown();
    }

    public static void main(String[] args) {
        launch(args);
    }
}