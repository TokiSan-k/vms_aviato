package com.aviato.Controllers;
import com.aviato.Main;
import com.aviato.Types.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Sales_Cltr {

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

    private final String customerPageHeading = "Manage Customers";
    private final String appointmentPageHeading = "Manage Appointments";
    private final String vehiclePageHeading = "Manage Vehicles";
    private final String servicePageHeading = "Manage Services";
    private final String employeePageHeading = "Manage Employee";


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

    @FXML
    private void handleHomePage(ActionEvent event) {
        if (!Main.currentStage.getScene().equals(Pages.GetMainMenuScene(Main.GetRoleName()))) {
            Main.currentStage.setScene(Pages.GetMainMenuScene(Main.GetRoleName()));
            Main.currentStage.setTitle("Home");
        }
    }

    @FXML
    private void handleCustomers(ActionEvent event) {
        if (!Main.currentStage.getScene().equals(Pages.GetCustomerScene())) {
            Main.currentStage.setScene(Pages.GetCustomerScene());
            Main.currentStage.setTitle(customerPageHeading);
        }
    }

    @FXML
    private void handleAppointments(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetAppointmentScene()) {
            Main.currentStage.setScene(Pages.GetAppointmentScene());
            Main.currentStage.setTitle(appointmentPageHeading);
        }
    }

    @FXML
    private void handleVehicles(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetVehicleScene()) {
            Main.currentStage.setScene(Pages.GetVehicleScene());
            Main.currentStage.setTitle(vehiclePageHeading);
            System.out.println("Width on Vehicle page: " + Main.currentStage.getWidth());
            System.out.println("Height on Vehicle page: " + Main.currentStage.getHeight());
        }
    }

    @FXML
    private void handlePayments(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetPaymentsScene()) {
            Main.currentStage.setScene(Pages.GetPaymentsScene());
            Main.currentStage.setTitle(customerPageHeading);
        }
    }

    @FXML
    private void handleServiceManagement(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetServiceManagementScene()) {
            Main.currentStage.setScene(Pages.GetServiceManagementScene());
            Main.currentStage.setTitle(servicePageHeading);
        }
    }

    @FXML
    private void handleEmployee(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetEmployeeScene()) {
            Main.currentStage.setScene(Pages.GetEmployeeScene());
            Main.currentStage.setTitle(employeePageHeading);
        }
    }

    @FXML
    private void handleNewAppointment(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetEmployeeScene()) {
            Main.currentStage.setScene(Pages.GetEmployeeScene());
            Main.currentStage.setTitle(employeePageHeading);
        }
    }

    @FXML
    private void handleCustomerSearch(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetEmployeeScene()) {
            Main.currentStage.setScene(Pages.GetEmployeeScene());
            Main.currentStage.setTitle(employeePageHeading);
        }
    }

    @FXML
    private void handleRegisterVehicle(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetEmployeeScene()) {
            Main.currentStage.setScene(Pages.GetEmployeeScene());
            Main.currentStage.setTitle(employeePageHeading);
        }
    }

    @FXML
    private void handleProcessPayment(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetEmployeeScene()) {
            Main.currentStage.setScene(Pages.GetEmployeeScene());
            Main.currentStage.setTitle(employeePageHeading);
        }
    }
}