package com.aviato.Controllers;

import com.aviato.Main;
import com.aviato.Types.Customer;
import com.aviato.Types.Pages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class SideNavBar_Cltr {
    // FXML injected buttons from the sidebar
    @FXML
    private Button sidebarCustomersButton;

    @FXML
    private Button sidebarAppointmentsButton;

    @FXML
    private Button sidebarVehiclesButton;

    @FXML
    private Button sidebarPaymentsButton;

    @FXML
    private Button sidebarManageRolesButton;

    @FXML
    private Button sidebarManageInventoryButton;

    @FXML
    private Button sidebarServiceManagementButton;

    @FXML
    private Button sidebarMonthlyReportsButton;

    @FXML
    private Button logOutButton;

    private final String customerPageHeading = "Manage Customers";
    private final String appointmentPageHeading = "Manage Appointments";
    private final String vehiclePageHeading = "Manage Vehicles";
    private final String manageRolesPageHeading = "Manage Roles";
    private final String inventoryPageHeading = "Manage Inventory";
    private final String servicePageHeading = "Manage Services";
    private final String employeePageHeading = "Manage Employee";

    // Handler methods for each button
    @FXML
    private void handleCustomers(ActionEvent event) {
        if(!Main.currentStage.getScene().equals(Pages.GetCustomerScene())){
            Main.currentStage.setScene(Pages.GetCustomerScene());
            Main.currentStage.setTitle(customerPageHeading);
        }
    }

    @FXML
    private void handleAppointments(ActionEvent event) {
        if(Main.currentStage.getScene() != Pages.GetAppointmentScene()){
            Main.currentStage.setScene(Pages.GetAppointmentScene());
            Main.currentStage.setTitle(appointmentPageHeading);
        }
    }

    @FXML
    private void handleVehicles(ActionEvent event) {
        if(Main.currentStage.getScene() != Pages.GetVehicleScene()){
            Main.currentStage.setScene(Pages.GetVehicleScene());
            Main.currentStage.setTitle(vehiclePageHeading);

            System.out.println("Width on Vehicle page: "+Main.currentStage.getWidth());
            System.out.println("Height on Vehicle page: "+Main.currentStage.getHeight());
        }
    }

    @FXML
    private void handlePayments(ActionEvent event) {
        if(Main.currentStage.getScene() != Pages.GetPaymentsScene()){
            Main.currentStage.setScene(Pages.GetPaymentsScene());
            Main.currentStage.setTitle(customerPageHeading);
        }
    }

    @FXML
    private void handleManageRoles(ActionEvent event) {
        if(Main.currentStage.getScene() != Pages.GetManageRoleScene()){
            Main.currentStage.setScene(Pages.GetManageRoleScene());
            Main.currentStage.setTitle(manageRolesPageHeading);
        }
    }

    @FXML
    private void handleManageInventory(ActionEvent event) {
        if(Main.currentStage.getScene() != Pages.GetInventoryScene()){
            Main.currentStage.setScene(Pages.GetInventoryScene());
            Main.currentStage.setTitle(inventoryPageHeading);
        }
    }

    @FXML
    private void handleServiceManagement(ActionEvent event) {
        if(Main.currentStage.getScene() != Pages.GetServiceManagementScene()){
            Main.currentStage.setScene(Pages.GetServiceManagementScene());
            Main.currentStage.setTitle(servicePageHeading);
        }
    }

    @FXML
    private void handleMonthlyReports(ActionEvent event) {
        if(Main.currentStage.getScene() != Pages.GetMonthlyReportsScene()){
            Main.currentStage.setScene(Pages.GetMonthlyReportsScene());
        }
    }

    @FXML
    private void handleEmployee(ActionEvent event) {
        if(Main.currentStage.getScene() != Pages.GetEmployeeScene()){
            Main.currentStage.setScene(Pages.GetEmployeeScene());
            Main.currentStage.setTitle(employeePageHeading);
        }
    }

    @FXML
    private void handleLogOut(ActionEvent event) {

    }


    // Optional: Initialize method if you need to set up anything when the controller loads
    @FXML
    public void initialize() {
        // Add any initialization logic here if needed
    }
}
