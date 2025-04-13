package com.aviato.Types;

import com.aviato.Main;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
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

    private static Scene paymentScene;
    private static Parent paymentRoot;

    private static Scene adminScene;
    private static Parent adminRoot;

    private static Scene salesScene;
    private static Parent salesRoot;

    private static Scene rolesScene;
    private static Parent rolesRoot;

    private static Scene loginScene;
    private static Parent loginRoot;

    private static Scene[] mainMenuScene = new Scene[2];

    private static double width;
    private static double height;

    public static void SetWidthHeightOfScreen(double Width, double Height)
    {
        width = Width;
        height = Height;
    }

    public static void LoadAllPages() throws Exception {
        try {

            loginRoot = new FXMLLoader(Main.class.getResource("/pages/Customer.fxml")).load();
            loginScene = new Scene(loginRoot);

            customerRoot = new FXMLLoader(Main.class.getResource("/pages/Customer.fxml")).load();
            customerScene = new Scene(customerRoot);

            inventoryRoot = new FXMLLoader(Main.class.getResource("/pages/Inventory.fxml")).load();
            inventoryScene = new Scene(inventoryRoot);

            vehicleRoot = new FXMLLoader(Main.class.getResource("/pages/Vehicle.fxml")).load();
            vehicleScene = new Scene(vehicleRoot);

            employeeRoot = new FXMLLoader(Main.class.getResource("/pages/Employee.fxml")).load();
            employeeScene = new Scene(employeeRoot);

            serviceRoot = new FXMLLoader(Main.class.getResource("/pages/Service.fxml")).load();
            serviceScene = new Scene(serviceRoot);

            appointmentRoot = new FXMLLoader(Main.class.getResource("/pages/Appointment.fxml")).load();
            appointmentScene = new Scene(appointmentRoot);

            paymentRoot = new FXMLLoader(Main.class.getResource("/pages/Payment.fxml")).load();
            paymentScene = new Scene(paymentRoot);

            adminRoot = new FXMLLoader(Main.class.getResource("/pages/Admin.fxml")).load();
            adminScene = new Scene(adminRoot);

            salesRoot = new FXMLLoader(Main.class.getResource("/pages/Sales.fxml")).load();
            salesScene = new Scene(salesRoot);

            loginRoot = new FXMLLoader(Main.class.getResource("/pages/Login.fxml")).load();
            loginScene = new Scene(loginRoot);

            rolesRoot = new FXMLLoader(Main.class.getResource("/pages/Roles.fxml")).load();
            rolesScene = new Scene(rolesRoot, width, height);

            mainMenuScene[0] = adminScene;
            mainMenuScene[1] = salesScene;
            // Debug output to verify sizes
            System.out.println("Visual Bounds - Width: " + width + ", Height: " + height);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static Scene GetCustomerScene() { return customerScene; }
    public static Scene GetInventoryScene() { return inventoryScene; }
    public static Scene GetAppointmentScene() { return appointmentScene; }
    public static Scene GetVehicleScene() { return vehicleScene; }
    public static Scene GetPaymentsScene() { return paymentScene; }
    public static Scene GetManageRoleScene() { return rolesScene; }
    public static Scene GetServiceManagementScene() {return serviceScene;}
    public static Scene GetMonthlyReportsScene() {return inventoryScene;}
    public static Scene GetEmployeeScene() {return employeeScene;}
    public static Scene GetAdminScene() {return adminScene;}
    public static Scene GetLogInScene() {return loginScene;}
    public static Scene GetMainMenuScene(String roleName)
    {
        //Change based on login
        for(int i =0; i<Policy.roleNames.size(); i++)
        {
            String t = Policy.roleNames.get(i);
            if(t.equals(roleName)) {
                return mainMenuScene[i];
            }
        }
        //Error

        return salesScene;
    }

}