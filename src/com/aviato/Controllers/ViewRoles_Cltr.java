package com.aviato.Controllers;

import com.aviato.Types.RoleItem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ViewRoles_Cltr {

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
    private Button removeInventoryButton;

    @FXML
    private Button updateInventoryButton;

    @FXML
    private Button viewInventoryButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<RoleItem> rolesTable;

    @FXML
    private TableColumn<RoleItem, String> idColumn;

    @FXML
    private TableColumn<RoleItem, String> employeeNameColumn;

    @FXML
    private TableColumn<RoleItem, String> positionColumn;

    @FXML
    private TableColumn<RoleItem, String> phoneNumberColumn;

    @FXML
    private TableColumn<RoleItem, String> emailColumn;

    @FXML
    private TableColumn<RoleItem, String> roleNameColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        employeeNameColumn.setCellValueFactory(cellData -> cellData.getValue().employeeNameProperty());
        positionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        roleNameColumn.setCellValueFactory(cellData -> cellData.getValue().roleNameProperty());
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
        System.out.println("Add Employee button clicked");
    }

    @FXML
    public void handleRemoveInventory() {
        System.out.println("Remove Employee button clicked");
    }

    @FXML
    public void handleUpdateInventory() {
        System.out.println("Update Employee button clicked");
    }

    @FXML
    public void handleViewInventory() {
        System.out.println("View Employee button clicked");
    }

    @FXML
    public void handleSearch() {
        String searchText = searchField.getText();
        System.out.println("Searching roles for: " + searchText);
    }
}
