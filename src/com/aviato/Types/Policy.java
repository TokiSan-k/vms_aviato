package com.aviato.Types;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Policy
{
    ///Map the Index with DB roleID
    public static final ObservableList<String> roleNames = FXCollections.observableArrayList("Admin", "Sales");

    public static void Autherize()
    {}

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
