package com.aviato.Types;

import com.aviato.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;

public class Pages {

    private static Scene customerScene;
    private static Parent customerRoot;

    private static Scene inventoryScene;
    private static Parent inventoryRoot;

    private static Scene vehicleScene;
    private static Parent vehicleRoot;

    private static Scene employeeScene;
    private static Parent employeeRoot;

    private static Scene serviceScene;
    private static Parent serviceRoot;

    private static Scene appointmentScene;
    private static Parent appointmentRoot;


    public static void LoadAllPages() throws Exception
    {
        try {
            double width = Screen.getPrimary().getVisualBounds().getWidth();
            double height = Screen.getPrimary().getVisualBounds().getHeight();

            customerRoot = new FXMLLoader(Main.class.getResource("/pages/Customer.fxml")).load();
            customerScene = new Scene(customerRoot, width, height);

            inventoryRoot = new FXMLLoader(Main.class.getResource("/pages/Inventory.fxml")).load();
            inventoryScene = new Scene(inventoryRoot, width, height);

            vehicleRoot = new FXMLLoader(Main.class.getResource("/pages/Vehicle.fxml")).load();
            vehicleScene = new Scene(vehicleRoot, width, height);

            employeeRoot = new FXMLLoader(Main.class.getResource("/pages/Employee.fxml")).load();
            employeeScene = new Scene(employeeRoot, width, height);

            serviceRoot = new FXMLLoader(Main.class.getResource("/pages/Service.fxml")).load();
            serviceScene = new Scene(serviceRoot, width, height);

            appointmentRoot = new FXMLLoader(Main.class.getResource("/pages/Appointment.fxml")).load();
            appointmentScene = new Scene(appointmentRoot, width, height);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static Scene GetCustomerScene() { return customerScene; }
    public static Scene GetInventoryScene() { return inventoryScene; }
    public static Scene GetAppointmentScene() { return appointmentScene; }
    public static Scene GetVehicleScene() { return vehicleScene; }
    public static Scene GetPaymentsScene() { return inventoryScene; }
    public static Scene GetManageRoleScene() { return inventoryScene; }
    public static Scene GetServiceManagementScene() {return serviceScene;}
    public static Scene GetMonthlyReportsScene() {return inventoryScene;}
    public static Scene GetLogOutScene() {return inventoryScene;}
    public static Scene GetEmployeeScene() {return employeeScene;}

}