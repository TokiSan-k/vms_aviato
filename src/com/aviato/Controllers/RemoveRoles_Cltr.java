package com.aviato.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RemoveRoles_Cltr {

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
    private Button addEmployeeButton;

    @FXML
    private Button removeEmployeeButton;

    @FXML
    private Button updateEmployeeButton;

    @FXML
    private Button viewEmployeeButton;

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
    public void handleAddEmployee() {
        System.out.println("Add Employee button clicked");
    }

    @FXML
    public void handleRemoveEmployee() {
        System.out.println("Remove Employee button clicked");
    }

    @FXML
    public void handleUpdateEmployee() {
        System.out.println("Update Employee button clicked");
    }

    @FXML
    public void handleViewEmployee() {
        System.out.println("View Employee button clicked");
    }

    @FXML
    public void handleRemove() {
        String employeeId = usernameField.getText();
        String employeeName = passwordField.getText();

        System.out.println("Remove button clicked with details:");
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Employee Name: " + employeeName);
    }

    @FXML
    public void handleCancel() {
        System.out.println("Cancel button clicked");
    }
}
