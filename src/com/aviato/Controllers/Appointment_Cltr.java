package com.aviato.Controllers;

import com.aviato.Types.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.Timestamp;

public class Appointment_Cltr {
    @FXML
    private VBox mainContainer;

    private VBox[] appointmentContainers = new VBox[5];
    private class AppContainerEnum {
        public static String AppointmentContainerTag = "#appointmentContainer_";
        public static final byte AddAppointmentContainer = 0;
        public static final byte RemoveAppointmentContainer = 1;
        public static final byte ModifyAppointmentContainer = 2;
        public static final byte ViewAppointmentContainer = 3;
        public static final byte ManageInvoiceContainer = 4;
    }

    @FXML
    private Button addAppBtn;
    @FXML
    private Button removeAppBtn;
    @FXML
    private Button modifyAppBtn;
    @FXML
    private Button viewAppBtn;

    @FXML
    private TextField aa_custIdField;
    @FXML
    private TextField aa_vehicleIdField;
    @FXML
    private TextField aa_appDateField;
    @FXML
    private TextField aa_appTimeField;
    @FXML
    private TextField aa_statusField;
    @FXML
    private TextField aa_serviceIdField;
    @FXML
    private TextField aa_empIdField;
    @FXML
    private Button aa_submitButton;

    @FXML
    private TextField ra_appIdField;
    @FXML
    private Button ra_swapFieldButton;
    @FXML
    private Button ra_submitButton;
    @FXML
    private TextField ra_appointmentSearchField;
    @FXML
    private Button ra_searchButton;
    @FXML
    private TableView<Appointment> ra_appointmentTable;
    @FXML
    private TableColumn<Appointment, Long> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, Date> appointmentDateColumn;
    @FXML
    private TableColumn<Appointment, String> appointmentStatusColumn;

    @FXML
    private TextField ma_appIdField;
    @FXML
    private Button ma_verifyButton;
    @FXML
    private TextField ma_custIdField;
    @FXML
    private TextField ma_vehicleIdField;
    @FXML
    private TextField ma_appDateField;
    @FXML
    private TextField ma_appTimeField;
    @FXML
    private TextField ma_statusField;
    @FXML
    private TextField ma_serviceIdField;
    @FXML
    private TextField ma_empIdField;
    @FXML
    private Button ma_editCustIdButton;
    @FXML
    private Button ma_editVehicleIdButton;
    @FXML
    private Button ma_editAppDateButton;
    @FXML
    private Button ma_editAppTimeButton;
    @FXML
    private Button ma_editStatusButton;
    @FXML
    private Button ma_editServiceIdButton;
    @FXML
    private Button ma_editEmpIdButton;
    @FXML
    private Button ma_submitButton;

    @FXML
    private TextField va_appointmentSearchField;
    @FXML
    private Button va_clearButton;
    @FXML
    private Button va_searchButton;
    @FXML
    private Button va_allAppointmentsButton;
    @FXML
    private TableView<Appointment> va_appointmentTable;
    @FXML
    private TableColumn<Appointment, Long> va_appIdColumn;
    @FXML
    private TableColumn<Appointment, Long> va_custIdColumn;
    @FXML
    private TableColumn<Appointment, Long> va_vehicleIdColumn;
    @FXML
    private TableColumn<Appointment, Date> va_appDateColumn;
    @FXML
    private TableColumn<Appointment, Timestamp> va_appTimeColumn;
    @FXML
    private TableColumn<Appointment, String> va_statusColumn;
    @FXML
    private TableColumn<Appointment, Long> va_serviceIdColumn;
    @FXML
    private TableColumn<Appointment, Long> va_empIdColumn;

    @FXML
    public void initialize() {
        for (byte i = 0; i < appointmentContainers.length; i++) {
            String container = AppContainerEnum.AppointmentContainerTag + i;
            appointmentContainers[i] = (VBox) mainContainer.lookup(container);
        }
        appointmentContainers[AppContainerEnum.AddAppointmentContainer].setVisible(true);
        appointmentContainers[AppContainerEnum.AddAppointmentContainer].setManaged(true);

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        appointmentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        va_appIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        va_custIdColumn.setCellValueFactory(new PropertyValueFactory<>("custId"));
        va_vehicleIdColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        va_appDateColumn.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        va_appTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appTime"));
        va_statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        va_serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        va_empIdColumn.setCellValueFactory(new PropertyValueFactory<>("empId"));
    }

    private void turnOffVisibleAndManageAppContainer() {
        for (byte i = 0; i < appointmentContainers.length; i++) {
            appointmentContainers[i].setVisible(false);
            appointmentContainers[i].setManaged(false);
        }
    }

    @FXML
    private void handleAppointmentNavAddApp(ActionEvent event) {
        turnOffVisibleAndManageAppContainer();
        appointmentContainers[AppContainerEnum.AddAppointmentContainer].setManaged(true);
        appointmentContainers[AppContainerEnum.AddAppointmentContainer].setVisible(true);
    }

    @FXML
    private void handleAppointmentNavRemoveApp(ActionEvent event) {
        turnOffVisibleAndManageAppContainer();
        appointmentContainers[AppContainerEnum.RemoveAppointmentContainer].setManaged(true);
        appointmentContainers[AppContainerEnum.RemoveAppointmentContainer].setVisible(true);
    }

    @FXML
    private void handleAppointmentNavModifyApp(ActionEvent event) {
        turnOffVisibleAndManageAppContainer();
        appointmentContainers[AppContainerEnum.ModifyAppointmentContainer].setManaged(true);
        appointmentContainers[AppContainerEnum.ModifyAppointmentContainer].setVisible(true);
    }

    @FXML
    private void handleAppointmentNavViewApp(ActionEvent event) {
        turnOffVisibleAndManageAppContainer();
        appointmentContainers[AppContainerEnum.ViewAppointmentContainer].setManaged(true);
        appointmentContainers[AppContainerEnum.ViewAppointmentContainer].setVisible(true);
    }

    @FXML
    private void handleAppointmentNavInvoice(ActionEvent event) {
        turnOffVisibleAndManageAppContainer();
        appointmentContainers[AppContainerEnum.ManageInvoiceContainer].setManaged(true);
        appointmentContainers[AppContainerEnum.ManageInvoiceContainer].setVisible(true);
    }

    @FXML
    private void submitAddAppointment(ActionEvent event) {
        aa_custIdField.clear();
        aa_vehicleIdField.clear();
        aa_appDateField.clear();
        aa_appTimeField.clear();
        aa_statusField.clear();
        aa_serviceIdField.clear();
        aa_empIdField.clear();
    }

    @FXML
    private void submitRemoveAppointment(ActionEvent event) {
        ra_appIdField.clear();
    }

    @FXML
    private void swapRemoveField(ActionEvent event) {
        System.out.println("Swap field button clicked in Remove Appointment");
    }

    @FXML
    private void searchRemoveAppointment(ActionEvent event) {
        String searchTerm = ra_appointmentSearchField.getText();
        System.out.println("Searching for appointment date: " + searchTerm);
    }

    @FXML
    private void verifyAppointmentId(ActionEvent event) {
        String appId = ma_appIdField.getText();
        System.out.println("Verifying appointment ID: " + appId);
    }

    @FXML
    private void editCustId(ActionEvent event) {
        ma_custIdField.setEditable(true);
    }

    @FXML
    private void editVehicleId(ActionEvent event) {
        ma_vehicleIdField.setEditable(true);
    }

    @FXML
    private void editAppDate(ActionEvent event) {
        ma_appDateField.setEditable(true);
    }

    @FXML
    private void editAppTime(ActionEvent event) {
        ma_appTimeField.setEditable(true);
    }

    @FXML
    private void editStatus(ActionEvent event) {
        ma_statusField.setEditable(true);
    }

    @FXML
    private void editServiceId(ActionEvent event) {
        ma_serviceIdField.setEditable(true);
    }

    @FXML
    private void editEmpId(ActionEvent event) {
        ma_empIdField.setEditable(true);
    }

    @FXML
    private void submitModifyAppointment(ActionEvent event) {
        System.out.println("Submitting modified appointment");
    }

    @FXML
    private void clearViewSearch(ActionEvent event) {
        va_appointmentSearchField.clear();
        va_appointmentTable.getItems().clear();
    }

    @FXML
    private void searchViewAppointment(ActionEvent event) {
        String searchTerm = va_appointmentSearchField.getText();
        System.out.println("Searching for appointment date: " + searchTerm);
    }

    @FXML
    private void showAllAppointments(ActionEvent event) {
        System.out.println("Showing all appointments");
    }
}