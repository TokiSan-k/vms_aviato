package com.aviato.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.aviato.Types.InventoryItem;

public class ViewInventory_Cltr {

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
    private TextField searchField;

    @FXML
    private TableView<InventoryItem> inventoryTable;

    @FXML
    private TableColumn<InventoryItem, String> partIdColumn;

    @FXML
    private TableColumn<InventoryItem, String> partNameColumn;

    @FXML
    private TableColumn<InventoryItem, Integer> quantityColumn;

    @FXML
    private TableColumn<InventoryItem, Double> pricePerUnitColumn;

    @FXML
    public void initialize() {
        partIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        pricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().pricePerUnitProperty().asObject());
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
    }

    @FXML
    public void handleRemoveInventory() {
        System.out.println("Remove Inventory button clicked");
    }

    @FXML
    public void handleUpdateInventory() {
        System.out.println("Update Inventory button clicked");
    }

    @FXML
    public void handleViewInventory() {
        System.out.println("View Inventory button clicked");
    }

    @FXML
    public void handleSearch() {
        String searchText = searchField.getText();
        System.out.println("Searching inventory for: " + searchText);
    }
}
