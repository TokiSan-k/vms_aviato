package com.aviato.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RemoveInventory_Cltr {

    @FXML
    private Button customersButton;

    @FXML
    private Button appointmentsButton;

    @FXML
    private Button vehiclesButton;

    @FXML
    private Button paymentsButton;

    @FXML
    private Button manageRolesButton;

    @FXML
    private Button manageInventoryButton;

    @FXML
    private Button serviceManagementButton;

    @FXML
    private Button monthlyReportsButton;

    @FXML
    private Button addInventoryButton;

    @FXML
    private Button removeInventoryButton;

    @FXML
    private Button updateInventoryButton;

    @FXML
    private Button viewInventoryButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    public void initialize() {
        // Initialization code if needed
    }

    @FXML
    public void handleCustomers() {
        System.out.println("Customers button clicked");
    }

    @FXML
    public void handleAppointments() {
        System.out.println("Appointments button clicked");
    }

    @FXML
    public void handleVehicles() {
        System.out.println("Vehicles button clicked");
    }

    @FXML
    public void handlePayments() {
        System.out.println("Payments button clicked");
    }

    @FXML
    public void handleManageRoles() {
        System.out.println("Manage Roles button clicked");
    }

    @FXML
    public void handleManageInventory() {
        System.out.println("Manage Inventory button clicked");
    }

    @FXML
    public void handleServiceManagement() {
        System.out.println("Service Management button clicked");
    }

    @FXML
    public void handleMonthlyReports() {
        System.out.println("Monthly Reports button clicked");
    }

    @FXML
    public void handleMainMenu() {
        System.out.println("Main Menu button clicked");
    }

    @FXML
    public void handleAddInventory() {
        System.out.println("Add Inventory button clicked");
        // Add logic to handle adding an Inventory
    }

    @FXML
    public void handleRemoveInventory() {
        System.out.println("Remove Inventory button clicked");
        // Add logic to handle removing an Inventory
    }

    @FXML
    public void handleUpdateInventory() {
        System.out.println("Update Inventory button clicked");
        // Add logic to handle updating an Inventory
    }

    @FXML
    public void handleViewInventory() {
        System.out.println("View Inventory button clicked");
        // Add logic to handle viewing an Inventory
    }

    @FXML
    public void handleRemove() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Remove Inventory button clicked with details:");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }

    @FXML
    public void handleCancel() {
        System.out.println("Cancel button clicked");
    }
}
