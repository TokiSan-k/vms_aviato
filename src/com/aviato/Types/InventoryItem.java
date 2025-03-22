package com.aviato.Types;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InventoryItem {

    private final SimpleStringProperty partId = new SimpleStringProperty();
    private final SimpleStringProperty partName = new SimpleStringProperty();
    private final SimpleIntegerProperty quantity = new SimpleIntegerProperty();
    private final SimpleDoubleProperty pricePerUnit = new SimpleDoubleProperty();

    public InventoryItem(String partId, String partName, int quantity, double pricePerUnit) {
        setPartId(partId);
        setPartName(partName);
        setQuantity(quantity);
        setPricePerUnit(pricePerUnit);
    }

    public String getPartId() {
        return partId.get();
    }

    public void setPartId(String partId) {
        this.partId.set(partId);
    }

    public SimpleStringProperty partIdProperty() {
        return partId;
    }

    public String getPartName() {
        return partName.get();
    }

    public void setPartName(String partName) {
        this.partName.set(partName);
    }

    public SimpleStringProperty partNameProperty() {
        return partName;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit.get();
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit.set(pricePerUnit);
    }

    public SimpleDoubleProperty pricePerUnitProperty() {
        return pricePerUnit;
    }
}
