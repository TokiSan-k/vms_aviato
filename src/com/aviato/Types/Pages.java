package com.aviato.Types;

import com.aviato.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Pages {

    private static Scene customerScene;
    private static Parent customerRoot;

    private static Scene inventoryScene;
    private static Parent inventoryRoot;

    public static void LoadAllPages() throws Exception
    {
        try {
            customerRoot = new FXMLLoader(Main.class.getResource("/pages/Customer.fxml")).load();
            customerScene = new Scene(customerRoot);

            inventoryRoot = new FXMLLoader(Main.class.getResource("/pages/AddInventory.fxml")).load();
            inventoryScene = new Scene(inventoryRoot);
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    public static Scene GetCustomerScene() { return customerScene; }
    public static Scene GetInventoryScene() { return inventoryScene; }
}