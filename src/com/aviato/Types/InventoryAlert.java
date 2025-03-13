package com.aviato.Types;

// Helper class for inventory alerts table
public class InventoryAlert {
    private String item;
    private String status;

    public InventoryAlert(String item, String status) {
        this.item = item;
        this.status = status;
    }

    public String getItem() {
        return item;
    }

    public String getStatus() {
        return status;
    }
}