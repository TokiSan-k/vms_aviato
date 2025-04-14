package com.aviato.Controllers;
import com.aviato.Main;
import com.aviato.Types.*;
import com.aviato.Utils.AlertBox;
import com.aviato.Utils.ErrorHandler;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.db.dao.Appointment_dao;
import com.aviato.db.dao.Inventory_dao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Admin_Cltr {

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
    @FXML private TableView<Appointment> va_appointmentTable;
    @FXML private TableColumn<Appointment, Long> va_appIdColumn;
    @FXML private TableColumn<Appointment, Long> va_custIdColumn;
    @FXML private TableColumn<Appointment, Long> va_vehicleIdColumn;
    @FXML private TableColumn<Appointment, Date> va_appDateColumn;
    @FXML private TableColumn<Appointment, Timestamp> va_appTimeColumn;
    @FXML private TableColumn<Appointment, String> va_statusColumn;
    @FXML private TableColumn<Appointment, Long> va_serviceIdColumn;
    @FXML private TableColumn<Appointment, Long> va_empIdColumn;
    private final ObservableList<Appointment> va_AppList = FXCollections.observableArrayList();

    private final ObservableList<InventoryAlert> vi_AlertList = FXCollections.observableArrayList();

    @FXML private Button vi_alertsButton;
    @FXML private TableView<InventoryAlert> vi_AlertsTable;
    @FXML private TableColumn<InventoryAlert, Long> vi_AlertItemId;
    @FXML private TableColumn<InventoryAlert, String> vi_AlertItemName;
    @FXML private TableColumn<InventoryAlert, Integer> vi_AlertQuantity;
    @FXML private TableColumn<InventoryAlert, Double> vi_AlertPPU;
    @FXML private TableColumn<InventoryAlert, Long> vi_AlertServiceId;
    @FXML private TableColumn<InventoryAlert, Integer> vi_AlertQuantityUsed;
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
    private final String manageRolesPageHeading = "Manage Roles";
    private final String inventoryPageHeading = "Manage Inventory";
    private final String servicePageHeading = "Manage Services";
    private final String employeePageHeading = "Manage Employee";


    public void OnLoad(){
        showTodaysAppointments();
        showInventoryAlerts();
        System.out.println("ADMINLOAD");
    }

    // Initialize the controller
    @FXML
    public void initialize() {
        // Set up View Appointment table columns
        va_appIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        va_custIdColumn.setCellValueFactory(new PropertyValueFactory<>("custId"));
        va_vehicleIdColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        va_appDateColumn.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        va_appTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appTime"));
        va_statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        va_serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        va_empIdColumn.setCellValueFactory(new PropertyValueFactory<>("empId"));
        va_appointmentTable.setItems(va_AppList);

        vi_AlertsTable.setItems(vi_AlertList);
        vi_AlertItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        vi_AlertItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        vi_AlertQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        vi_AlertPPU.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
        vi_AlertServiceId.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        vi_AlertQuantityUsed.setCellValueFactory(new PropertyValueFactory<>("quantityUsed"));
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
    private void handleManageRoles(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetManageRoleScene()) {
            Main.currentStage.setScene(Pages.GetManageRoleScene());
            Main.currentStage.setTitle(manageRolesPageHeading);
        }
    }

    @FXML
    private void handleManageInventory(ActionEvent event) {
        if (Main.currentStage.getScene() != Pages.GetInventoryScene()) {
            Main.currentStage.setScene(Pages.GetInventoryScene());
            Main.currentStage.setTitle(inventoryPageHeading);
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

    private void showTodaysAppointments() {
        try {
            Task<List<Appointment>> getTodaysTask = Appointment_dao.getTodaysAppointmentsTask();
            getTodaysTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    va_AppList.clear();
                    va_AppList.addAll(getTodaysTask.getValue());
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Today's appointments loaded");
                });
            });

            getTodaysTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getTodaysTask.getException());
                });
            });

            Worker.submitTask(getTodaysTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch today's appointments: " + e.getMessage());
        }
    }

    @FXML
    private void showInventoryAlerts() {
        try {
            Task<List<InventoryAlert>> getAlertsTask = Inventory_dao.getInventoryAlertsTask();
            getAlertsTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    vi_AlertList.clear();
                    vi_AlertList.addAll(getAlertsTask.getValue());
                });
            });

            getAlertsTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAlertsTask.getException());
                });
            });

            Worker.submitTask(getAlertsTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch inventory alerts: " + e.getMessage());
        }
    }
}