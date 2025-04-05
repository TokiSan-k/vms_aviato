package com.aviato.Controllers;

import com.aviato.Main;
import com.aviato.Types.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NavBar_Cltr {

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

    // Handler methods for each button
    @FXML
    private void handleCustomers(ActionEvent event) {
        if(Main.currentStage.getScene() != Pages.GetCustomerScene()){
            Main.currentStage.setScene(Pages.GetCustomerScene());
        }
    }

    @FXML
    private void handleAppointments(ActionEvent event) {
        // Add logic to switch to Appointments view
        System.out.println("Navigating to Appointments view");
        // Example: loadAppointmentsView();
    }

    @FXML
    private void handleVehicles(ActionEvent event) {
        // Add logic to switch to Vehicles view
        System.out.println("Navigating to Vehicles view");
        // Example: loadVehiclesView();
    }

    @FXML
    private void handlePayments(ActionEvent event) {
        // Add logic to switch to Payments view
        System.out.println("Navigating to Payments view");
        // Example: loadPaymentsView();
    }

    @FXML
    private void handleManageRoles(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetManageRoleScene()) {
            Main.currentStage.setScene(Pages.GetManageRoleScene());
        }
    }

    @FXML
    private void handleManageInventory(ActionEvent event) {
        if(Main.currentStage.getScene() != Pages.GetInventoryScene()){
            Main.currentStage.setScene(Pages.GetInventoryScene());
        }
    }

    @FXML
    private void handleServiceManagement(ActionEvent event) {
        // Add logic to switch to Service Management view
        System.out.println("Navigating to Service Management view");
        // Example: loadServiceManagementView();
    }

    @FXML
    private void handleMonthlyReports(ActionEvent event) {
        // Add logic to switch to Monthly Reports view
        System.out.println("Navigating to Monthly Reports view");
        // Example: loadMonthlyReportsView();
    }

    @FXML
    private void handleLogOut(ActionEvent event) {
        // Add logout logic here
        System.out.println("Logging out...");
        // Example: performLogout();
    }


    @FXML
    public void initialize() {

    }
}