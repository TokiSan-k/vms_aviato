package com.aviato.Types;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Policy
{
    ///Map the Index with DB roleID
    public static final ObservableList<String> roleNames = FXCollections.observableArrayList("Admin", "Sales Representative");

    public static final String[] AdminPolicy = {"Customer_page", "Vehicle_page", "Employee_page", "Service_page", "Appointment_page", "Payment_page", "Inventory_page", "User_page"};
    public static final String[] SalesPolicy = {"Customer_page", "Vehicle_page", "Employee_page", "Service_page", "Appointment_page", "Payment_page"};

    public static long GetRoleId(String roleName)
    {
        for(int i =0; i<roleNames.size(); i++)
        {
            if(roleNames.get(i) == roleName)
                return (long)i;
        }
        return -1l;
    }


}
