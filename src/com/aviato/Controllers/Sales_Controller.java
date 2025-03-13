package com.aviato.Controllers;
import com.aviato.Types.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Sales_Controller {

    // Sidebar menu buttons
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

    // Pie chart for pending services
    @FXML
    private PieChart pendingServicesChart;

    // Table for today's appointments
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, String> detailsColumn;
    @FXML
    private TableColumn<Appointment, String> timeColumn;

    // Quick action buttons
    @FXML
    private Button newAppointmentButton;
    @FXML
    private Button customerSearchButton;
    @FXML
    private Button registerVehicleButton;
    @FXML
    private Button processPaymentButton;

    // Table for inventory alerts (placeholders for columns Item and Status)
    @FXML
    private TableView<InventoryAlert> inventoryTable;
    @FXML
    private TableColumn<InventoryAlert, String> c1Column;
    @FXML
    private TableColumn<InventoryAlert, String> c2Column;

    // Initialize the controller
    @FXML
    public void initialize() {
//        // Initialize the pie chart with sample data
//        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
//                new PieChart.Data("Detailing (35%)", 35),
//                new PieChart.Data("PPF Install (25%)", 25),
//                new PieChart.Data("Ceramic Coating (20%)", 20),
//                new PieChart.Data("Oil Change (15%)", 15),
//                new PieChart.Data("Other (5%)", 5)
//        );
//        pendingServicesChart.setData(pieChartData);
//
//        // Initialize the appointments table
//        detailsColumn.setCellValueFactory(new PropertyValueFactory<>("details"));
//        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
//
//        ObservableList<Appointment> appointments = FXCollections.observableArrayList(
//                new Appointment("John Smith", "9:00 Am"),
//                new Appointment("Jay Jones", "10:00 Am"),
//                new Appointment("Alex", "10:00 Am"),
//                new Appointment("Tom", "10:00 Am")
//        );
//        appointmentsTable.setItems(appointments);
//
//        // Initialize the inventory alerts table (placeholder data)
//        c1Column.setCellValueFactory(new PropertyValueFactory<>("item"));
//        c2Column.setCellValueFactory(new PropertyValueFactory<>("status"));
//
//        ObservableList<InventoryAlert> inventoryAlerts = FXCollections.observableArrayList(
//                // Add placeholder data if needed, e.g., new InventoryAlert("Oil Filter", "Low Stock")
//        );
//        inventoryTable.setItems(inventoryAlerts);


    }

    // Event handlers for sidebar buttons
    @FXML
    private void handleCustomers() {
        System.out.println("Navigating to Customers section...");
        // Add navigation logic here
    }

    @FXML
    private void handleAppointments() {
        System.out.println("Navigating to Appointments section...");
        // Add navigation logic here
    }

    @FXML
    private void handleVehicles() {
        System.out.println("Navigating to Vehicles section...");
        // Add navigation logic here
    }

    @FXML
    private void handlePayments() {
        System.out.println("Navigating to Payments section...");
        // Add navigation logic here
    }

    @FXML
    private void handleManageRoles() {
        System.out.println("Navigating to Manage Roles section...");
        // Add navigation logic here
    }

    @FXML
    private void handleManageInventory() {
        System.out.println("Navigating to Manage Inventory section...");
        // Add navigation logic here
    }

    @FXML
    private void handleServiceManagement() {
        System.out.println("Navigating to Service Management section...");
        // Add navigation logic here
    }

    @FXML
    private void handleMonthlyReports() {
        System.out.println("Navigating to Monthly Reports section...");
        // Add navigation logic here
    }

    // Event handlers for quick action buttons
    @FXML
    private void handleNewAppointment() {
        System.out.println("Opening New Appointment form...");
        // Add logic to open a new appointment form
    }

    @FXML
    private void handleCustomerSearch() {
        System.out.println("Opening Customer Search...");
        // Add logic to open customer search
    }

    @FXML
    private void handleRegisterVehicle() {
        System.out.println("Opening Register Vehicle form...");
        // Add logic to open vehicle registration form
    }

    @FXML
    private void handleProcessPayment() {
        System.out.println("Opening Process Payment form...");
        // Add logic to open payment processing form
    }
}