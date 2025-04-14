package com.aviato.Controllers;

import com.aviato.Main;
import com.aviato.Types.Pages;
import com.aviato.Types.Policy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SideNavBar_Cltr {

    @FXML
    private VBox mainContainer;

    // Match FXML fx:id values
    @FXML
    private Button sidebarHomeButton;

    @FXML
    private Button Customer_page;

    @FXML
    private Button Vehicle_page;

    @FXML
    private Button Employee_page;

    @FXML
    private Button Service_page;

    @FXML
    private Button Appointment_page;

    @FXML
    private Button Payment_page;

    @FXML
    private Button Inventory_page;

    @FXML
    private Button User_page;

    @FXML
    private Button logOutButton;

    private final String customerPageHeading = "Manage Customers";
    private final String appointmentPageHeading = "Manage Appointments";
    private final String vehiclePageHeading = "Manage Vehicles";
    private final String manageRolesPageHeading = "Manage Roles";
    private final String inventoryPageHeading = "Manage Inventory";
    private final String servicePageHeading = "Manage Services";
    private final String employeePageHeading = "Manage Employee";

    public void AdaptToPolicy() {
        // Hide all buttons by default (except Home and Log Out, which are always visible)
        Customer_page.setVisible(false);
        Customer_page.setManaged(false);
        Vehicle_page.setVisible(false);
        Vehicle_page.setManaged(false);
        Employee_page.setVisible(false);
        Employee_page.setManaged(false);
        Service_page.setVisible(false);
        Service_page.setManaged(false);
        Appointment_page.setVisible(false);
        Appointment_page.setManaged(false);
        Payment_page.setVisible(false);
        Payment_page.setManaged(false);
        Inventory_page.setVisible(false);
        Inventory_page.setManaged(false);
        User_page.setVisible(false);
        User_page.setManaged(false);

        String roleName = Main.GetRoleName();
        if (roleName == null) {
            System.err.println("Role name is null");
            return;
        }

        if (roleName.equals(Policy.roleNames.get(0))) { // Admin
            for (String id : Policy.AdminPolicy) {
                Button button = getButtonById(id);
                if (button != null) {
                    button.setVisible(true);
                    button.setManaged(true);
                } else {
                    System.err.println("Button not found for ID: " + id);
                }
            }
        } else if (roleName.equals(Policy.roleNames.get(1))) { // Sales
            for (String id : Policy.SalesPolicy) {
                Button button = getButtonById(id);
                if (button != null) {
                    button.setVisible(true);
                    button.setManaged(true);
                } else {
                    System.err.println("Button not found for ID: " + id);
                }
            }
        }
    }

    private Button getButtonById(String id) {
        switch (id) {
            case "Customer_page": return Customer_page;
            case "Vehicle_page": return Vehicle_page;
            case "Employee_page": return Employee_page;
            case "Service_page": return Service_page;
            case "Appointment_page": return Appointment_page;
            case "Payment_page": return Payment_page;
            case "Inventory_page": return Inventory_page;
            case "User_page": return User_page;
            default: return null;
        }
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
    private void handleLogOut(ActionEvent event) {
        Main.currentStage.setScene(Pages.GetLogInScene());
        Main.currentStage.setTitle("LogIn");
    }

    public void ApplyHighlight(String id){
        Button button = getButtonById(id);
        button.getStyleClass().add("button-selected");
    }

    @FXML
    private void handleMainMenu()
    {
        Main.currentStage.setScene(Pages.GetMainMenuScene(Main.GetRoleName()));
        Main.currentStage.setTitle("Main Menu");
    }

    @FXML
    public void initialize() {
//        Main.sideNavIns = this;
        Main.AllsideNavIns.add(this);
        //AdaptToPolicy();
    }
}