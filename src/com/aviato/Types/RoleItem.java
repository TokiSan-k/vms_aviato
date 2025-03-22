package com.aviato.Types;

import javafx.beans.property.SimpleStringProperty;

public class RoleItem {

    private final SimpleStringProperty id = new SimpleStringProperty();
    private final SimpleStringProperty employeeName = new SimpleStringProperty();
    private final SimpleStringProperty position = new SimpleStringProperty();
    private final SimpleStringProperty phoneNumber = new SimpleStringProperty();
    private final SimpleStringProperty email = new SimpleStringProperty();
    private final SimpleStringProperty roleName = new SimpleStringProperty();

    public RoleItem(String id, String employeeName, String position, String phoneNumber, String email, String roleName) {
        setId(id);
        setEmployeeName(employeeName);
        setPosition(position);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setRoleName(roleName);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName.set(employeeName);
    }

    public SimpleStringProperty employeeNameProperty() {
        return employeeName;
    }

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getRoleName() {
        return roleName.get();
    }

    public void setRoleName(String roleName) {
        this.roleName.set(roleName);
    }

    public SimpleStringProperty roleNameProperty() {
        return roleName;
    }
}
