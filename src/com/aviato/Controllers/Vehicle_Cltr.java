package com.aviato.Controllers;

import com.aviato.Types.Vehicle;
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

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class Vehicle_Cltr {
    // Containers
    @FXML
    private VBox mainContainer;

    private VBox[] vehicleContainers = new VBox[4];
    private class VehicleContainerEnum {
        public static String VehicleContainerTag = "#vehicleContainer_";
        public static final byte AddVehicleContainer = 0;
        public static final byte RemoveVehicleContainer = 1;
        public static final byte ModifyVehicleContainer = 2;
        public static final byte ViewVehicleContainer = 3;
    }

    // Vehicle Navbar
    @FXML
    private Button addVehicleBtn;
    @FXML
    private Button removeVehicleBtn;
    @FXML
    private Button modifyVehicleBtn;
    @FXML
    private Button viewVehicleBtn;

    // Add Vehicle Fields
    @FXML
    private TextField av_CustIdField;
    @FXML
    private Button av_verifyCustIdButton;
    @FXML
    private TextField ac_LicencePlateField;
    @FXML
    private TextField ac_MakeField;
    @FXML
    private TextField ac_ModelField;
    @FXML
    private TextField ac_YearField;

    // Remove Vehicle Fields
    @FXML
    private TextField rv_inputField;
    @FXML
    private Button rv_swapFieldButton;
    @FXML
    private TextField rv_vehicleSearchField;
    @FXML
    private Button rv_searchButton;
    @FXML
    private TableView<Vehicle> rv_vehicleTable;
    @FXML
    private TableColumn<Vehicle, String> vehicleIdColumn;
    @FXML
    private TableColumn<Vehicle, String> vehicleLicencePlateColumn;
    @FXML
    private TableColumn<Vehicle, String> vehicleMakeColumn;
    @FXML
    private TableColumn<Vehicle, String> vehicleModelColumn;
    @FXML
    private TableColumn<Vehicle, String> vehicleYearColumn;

    // Modify Vehicle Fields
    @FXML
    private TextField mv_vehicleIdField;
    @FXML
    private Button mv_verifyButton;
    @FXML
    private TextField mv_LicencePlateField;
    @FXML
    private TextField mv_MakeField;
    @FXML
    private TextField mv_ModelField;
    @FXML
    private TextField mv_YearField;
    @FXML
    private Button mv_editLicencePlateButton;
    @FXML
    private Button mc_editMakeButton;
    @FXML
    private Button mv_ModelButton;
    @FXML
    private Button mv_VehicleYearEditButton;

    // View Vehicle Fields
    @FXML
    private TextField vv_VehicleIdField;
    @FXML
    private Button vv_clearButton;
    @FXML
    private Button vv_searchButton;
    @FXML
    private Button vv_allVehicleButton;
    @FXML
    private TableView<Vehicle> vv_VehicleTable;
    @FXML
    private TableColumn<Vehicle, String> vv_VehicleIdColumn;
    @FXML
    private TableColumn<Vehicle, String> vv_CustomerIdColumn;
    @FXML
    private TableColumn<Vehicle, String> vv_LicencePlateColumn;
    @FXML
    private TableColumn<Vehicle, String> vv_MakeColumn;
    @FXML
    private TableColumn<Vehicle, String> vv_ModelColumn;
    @FXML
    private TableColumn<Vehicle, String> vv_YearColumn;

    // Initialize method to set up table columns
    @FXML
    public void initialize() {
        for (byte i = 0; i < vehicleContainers.length; i++) {
            String container = VehicleContainerEnum.VehicleContainerTag + i;
            vehicleContainers[i] = (VBox) mainContainer.lookup(container);
        }
        vehicleContainers[VehicleContainerEnum.AddVehicleContainer].setVisible(true);
        vehicleContainers[VehicleContainerEnum.AddVehicleContainer].setManaged(true);

        // Set up Remove Vehicle table columns
        vehicleIdColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        vehicleLicencePlateColumn.setCellValueFactory(new PropertyValueFactory<>("licencePlate"));
        vehicleMakeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        vehicleModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        vehicleYearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        // Set up View Vehicle table columns
        vv_VehicleIdColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        vv_CustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        vv_LicencePlateColumn.setCellValueFactory(new PropertyValueFactory<>("licencePlate"));
        vv_MakeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        vv_ModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        vv_YearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
    }

    // Vehicle NavBar
    private void turnOffVisibleAndManageVehicleContainer() {
        for (byte i = 0; i < vehicleContainers.length; i++) {
            vehicleContainers[i].setVisible(false);
            vehicleContainers[i].setManaged(false);
        }
    }

    @FXML
    private void handleCustomerNavAddCust(ActionEvent event) {
        turnOffVisibleAndManageVehicleContainer();
        vehicleContainers[VehicleContainerEnum.AddVehicleContainer].setManaged(true);
        vehicleContainers[VehicleContainerEnum.AddVehicleContainer].setVisible(true);
    }

    @FXML
    private void handleCustomerNavRemoveCust(ActionEvent event) {
        turnOffVisibleAndManageVehicleContainer();
        vehicleContainers[VehicleContainerEnum.RemoveVehicleContainer].setManaged(true);
        vehicleContainers[VehicleContainerEnum.RemoveVehicleContainer].setVisible(true);
    }

    @FXML
    private void handleCustomerNavModifyCust(ActionEvent event) {
        turnOffVisibleAndManageVehicleContainer();
        vehicleContainers[VehicleContainerEnum.ModifyVehicleContainer].setManaged(true);
        vehicleContainers[VehicleContainerEnum.ModifyVehicleContainer].setVisible(true);
    }

    @FXML
    private void handleCustomerNavViewCust(ActionEvent event) {
        turnOffVisibleAndManageVehicleContainer();
        vehicleContainers[VehicleContainerEnum.ViewVehicleContainer].setManaged(true);
        vehicleContainers[VehicleContainerEnum.ViewVehicleContainer].setVisible(true);
    }

    // Add Vehicle Submit Handler
    @FXML
    private void submitAddVehicle(ActionEvent event) {
        String customerId = av_CustIdField.getText();
        String licencePlate = ac_LicencePlateField.getText();
        String make = ac_MakeField.getText();
        String model = ac_ModelField.getText();
        String year = ac_YearField.getText();

        // Clear fields
        av_CustIdField.clear();
        ac_LicencePlateField.clear();
        ac_MakeField.clear();
        ac_ModelField.clear();
        ac_YearField.clear();
    }

    @FXML
    private void verifyCustomerId(ActionEvent event) {
        // Logic to verify customer ID would go here
        System.out.println("Verifying Customer ID: " + av_CustIdField.getText());
    }

    // Remove Vehicle Event Handlers
    @FXML
    private void submitRemoveVehicle(ActionEvent event) {
        // Logic to remove vehicle would go here
    }

    @FXML
    private void swapRemoveField(ActionEvent event) {
        System.out.println("Swap field button clicked in Remove Vehicle");
    }

    @FXML
    private void searchRemoveVehicle(ActionEvent event) {
        String searchTerm = rv_vehicleSearchField.getText();
        // Search logic would go here
    }

    // Modify Vehicle Event Handlers
    @FXML
    private void verifyVehicleId(ActionEvent event) {
        // Logic to verify vehicle ID would go here
        System.out.println("Verifying Vehicle ID: " + mv_vehicleIdField.getText());
    }

    @FXML
    private void editLicencePlate(ActionEvent event) {
        mv_LicencePlateField.setEditable(true);
    }

    @FXML
    private void editMake(ActionEvent event) {
        mv_MakeField.setEditable(true);
    }

    @FXML
    private void editModel(ActionEvent event) {
        mv_ModelField.setEditable(true);
    }

    @FXML
    private void editYear(ActionEvent event) {
        mv_YearField.setEditable(true);
    }

    @FXML
    private void submitModifyVehicle(ActionEvent event) {
        // Logic to submit modified vehicle would go here
    }

    // View Vehicle Event Handlers
    @FXML
    private void clearViewSearch(ActionEvent event) {
        vv_VehicleIdField.clear();
        vv_VehicleTable.getItems().clear();
    }

    @FXML
    private void searchViewVehicle(ActionEvent event) {
        String vehicleId = vv_VehicleIdField.getText();
        // Search logic would go here
    }

    @FXML
    private void showAllVehicles(ActionEvent event) {
        // Logic to show all vehicles would go here
    }
}
