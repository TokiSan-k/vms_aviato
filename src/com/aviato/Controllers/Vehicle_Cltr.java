package com.aviato.Controllers;

import com.aviato.Main;
import com.aviato.Types.Customer;
import com.aviato.Types.Pages;
import com.aviato.Types.Vehicle;
import com.aviato.Utils.AlertBox;
import com.aviato.Utils.ErrorHandler;
import com.aviato.Utils.Field;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.db.dao.Customer_dao;
import com.aviato.db.dao.Vehicle_dao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import java.util.Arrays;
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

    private final ObservableList<Vehicle> rv_vehicleList = FXCollections.observableArrayList();
    private final ObservableList<Vehicle> vv_vehicleList = FXCollections.observableArrayList();

    private final Field[] vvSwapFields = { new Field(0,"ID:","Enter Vehicle ID"),
            new Field(1,"Customer ID:","Enter Customer ID")};


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
    private TextField av_LicencePlateField;
    @FXML
    private TextField av_MakeField;
    @FXML
    private TextField av_ModelField;
    @FXML
    private TextField av_YearField;

    // Remove Vehicle Fields
    @FXML
    private Text rv_swapLabel;
    @FXML
    private TextField rv_swapField;
    @FXML
    private Button rv_tableSwapFieldButton;
    @FXML
    private Text rv_searchLabel;
    @FXML
    private TextField rv_searchField;
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
    private Text vv_SwapLabel;
    @FXML
    private TextField vv_SwapField;
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

    private Vehicle vehicle = new Vehicle();

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

        vv_VehicleTable.setItems(vv_vehicleList);
    }

    // Vehicle NavBar
    private void turnOffVisibleAndManageVehicleContainer() {
        for (byte i = 0; i < vehicleContainers.length; i++) {
            vehicleContainers[i].setVisible(false);
            vehicleContainers[i].setManaged(false);
        }

        SetEditableAVFields(false);
        SetEditableMVFields(false);
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

    private void clearAddVehicleFields() {
        av_CustIdField.clear();
        av_LicencePlateField.clear();
        av_MakeField.clear();
        av_ModelField.clear();
        av_YearField.clear();
    }

    // Add Vehicle Submit Handler
    @FXML
    private void submitAddVehicle(ActionEvent event) {
        try {
            Long custId = Long.parseLong(av_CustIdField.getText());
            String licencePlate = av_LicencePlateField.getText();
            String make = av_MakeField.getText();
            String model = av_ModelField.getText();
            int year = Integer.parseInt(av_YearField.getText());

            vehicle.SetAllFields(custId, licencePlate, make, model, year);
            Task<Void> insertTask = Vehicle_dao.insertVehicleTask(vehicle);

            insertTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearAddVehicleFields();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Vehicle added successfully");
                });
            });

            insertTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearAddVehicleFields();
                    ErrorHandler.ManageException(insertTask.getException());
                });
            });

            Worker.submitTask(insertTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    private void SetEditableAVFields(boolean status)
    {
        av_LicencePlateField.setEditable(status);
        av_LicencePlateField.setDisable(!status);

        av_MakeField.setEditable(status);
        av_MakeField.setDisable(!status);

        av_ModelField.setEditable(status);
        av_ModelField.setDisable(!status);

        av_YearField.setEditable(status);
        av_YearField.setDisable(!status);
    }

    @FXML
    private void verifyCustomerId(ActionEvent event) {
        try {
            String inputID = av_CustIdField.getText();
            if (inputID.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please Enter a Customer ID to remove");
                return;
            }
            Long custId = Long.parseLong(inputID);

            Task<Customer> getCustTask = Customer_dao.getCustomerTask(custId);
            getCustTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    //Verify UI update
                    SetEditableAVFields(true);
                    //======
                    //For Now
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Customer Verified!");
                });
            });

            getCustTask.setOnFailed(e -> {
                Platform.runLater(() ->{
                    ErrorHandler.ManageException(getCustTask.getException());
                });
            });

            //getCustTask.setOnFinished(e -> showLoading(false));
            Worker.submitTask(getCustTask);
        }
        catch (Exception ex)
        {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    // Remove Vehicle Event Handlers
    @FXML
    private void submitRemoveVehicle(ActionEvent event) {
        try {
            String inputID = rv_swapField.getText();
            if (inputID.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter a Vehicle ID to remove");
                return;
            }
            Long vehicleId = Long.parseLong(inputID);

            Task<Void> deleteTask = Vehicle_dao.deleteVehicleTask(vehicleId);
            deleteTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    rv_swapField.clear();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Vehicle removed successfully");
                });
            });

            deleteTask.setOnFailed(e -> {
                Platform.runLater(() ->{
                    ErrorHandler.ManageException(deleteTask.getException());
                });
            });

            Worker.submitTask(deleteTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    @FXML
    private void TableSearchRV(ActionEvent event) {
        try {
            String searchTerm = vv_SwapField.getText();
            if (searchTerm.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "CustomerID cannot be empty!");
                return;
            }
            try {
                Long customerId = Long.parseLong(searchTerm);
                Task<List<Vehicle>> searchTask = Vehicle_dao.searchVehiclesByCustomerIdTask(customerId);

                searchTask.setOnSucceeded(e -> {
                    Platform.runLater(() -> {
                        rv_vehicleList.clear();
                        rv_vehicleList.addAll(searchTask.getValue());
                        rv_searchField.clear();
                    });
                });

                searchTask.setOnFailed(e -> {
                    Platform.runLater(() -> {
                        ErrorHandler.ManageException(searchTask.getException());
                    });
                });

                Worker.submitTask(searchTask);
            } catch (NumberFormatException ex) {
                AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid Customer ID");
            }
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
    }

    @FXML
    private void SearchAllVehicle(ActionEvent event) {
        try{
            Task<List<Vehicle>> getAllVehicleTask = Vehicle_dao.getAllVehiclesTask();
            getAllVehicleTask.setOnSucceeded(e ->
            {
                Platform.runLater(() -> {
                    rv_vehicleList.clear();
                    rv_vehicleList.addAll(getAllVehicleTask.getValue());
                });
            });

            getAllVehicleTask.setOnFailed(e ->
            {
                Platform.runLater(() ->{
                    ErrorHandler.ManageException(getAllVehicleTask.getException());
                });
            });

            Worker.submitTask(getAllVehicleTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR,"Error", e.getMessage());
        }
    }

    // Modify Vehicle Event Handlers
    private void SetEditableMVFields(boolean status)
    {
        mv_LicencePlateField.setEditable(status);
        mv_LicencePlateField.setDisable(!status);

        mv_MakeField.setEditable(status);
        mv_MakeField.setDisable(!status);

        mv_ModelField.setEditable(status);
        mv_ModelField.setDisable(!status);

        mv_YearField.setEditable(status);
        mv_YearField.setDisable(!status);
    }

    @FXML
    private void verifyVehicleId(ActionEvent event) {
        try {
            String inputID = mv_vehicleIdField.getText();
            if (inputID.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please Enter a Customer ID to remove");
                return;
            }
            Long vehicleId = Long.parseLong(inputID);

            Task<Vehicle> getVehicleTask = Vehicle_dao.getVehicleTask(vehicleId);
            getVehicleTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    //Verify UI update
                    OnVehicleVerified(getVehicleTask.getValue());

                    //For Now
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Vehicle ID Verified!");
                });
            });

            getVehicleTask.setOnFailed(e -> {
                Platform.runLater(() ->{
                        ErrorHandler.ManageException(getVehicleTask.getException());
                });
            });

            //getCustTask.setOnFinished(e -> showLoading(false));
            Worker.submitTask(getVehicleTask);
        }
        catch (Exception ex)
        {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    private void OnVehicleVerified(Vehicle vehi)
    {
        this.vehicle.setCustomerId(vehi.getCustomerId());
        mv_LicencePlateField.setText(vehi.getLicencePlate());
        mv_MakeField.setText(vehi.getMake());
        mv_ModelField.setText(vehi.getModel());
        mv_YearField.setText(String.valueOf(vehi.getYear()));

        //UI
    }

    @FXML
    private void editLicencePlate(ActionEvent event) {
        mv_LicencePlateField.setEditable(true);
        mv_LicencePlateField.setDisable(false);
    }

    @FXML
    private void editMake(ActionEvent event) {
        mv_MakeField.setEditable(true);
        mv_MakeField.setDisable(false);
    }

    @FXML
    private void editModel(ActionEvent event) {
        mv_ModelField.setEditable(true);
        mv_ModelField.setDisable(false);
    }

    @FXML
    private void editYear(ActionEvent event) {
        mv_YearField.setEditable(true);
        mv_YearField.setDisable(false);
    }

    private void ClearAllMVFields()
    {
        mv_vehicleIdField.clear();
        mv_LicencePlateField.clear();
        mv_MakeField.clear();
        mv_ModelField.clear();
        mv_YearField.clear();
    }

    @FXML
    private void submitModifyVehicle(ActionEvent event) {
        try {
            vehicle.setVehicleId(Long.parseLong(mv_vehicleIdField.getText()));
            vehicle.SetAllFields(vehicle.getCustomerId(),
                    mv_LicencePlateField.getText(),
                    mv_MakeField.getText(),
                    mv_ModelField.getText(),
                    Integer.parseInt(mv_YearField.getText()));

            Task<Void> updateVehicleTask = Vehicle_dao.updateVehicleTask(vehicle);
            updateVehicleTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ClearAllMVFields();
                    SetEditableMVFields(false);
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Vehicle Modified successfully");
                });
            });

            updateVehicleTask.setOnFailed(e -> {
                Platform.runLater(() ->{
                    ClearAllMVFields();
                    SetEditableMVFields(false);
                    ErrorHandler.ManageException(updateVehicleTask.getException());
                });
            });

            //getVehicleTask.setOnFinished(e -> showLoading(false));
            Worker.submitTask(updateVehicleTask);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    @FXML
    private void searchViewVehicle(ActionEvent event) {
        try {
            String searchTerm = vv_SwapField.getText();
            if (searchTerm.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "CustomerID cannot be empty!");
                return;
            }
            try {
                Long customerId = Long.parseLong(searchTerm);
                Task<List<Vehicle>> searchTask = Vehicle_dao.searchVehiclesByCustomerIdTask(customerId);

                searchTask.setOnSucceeded(e -> {
                    Platform.runLater(() -> {
                        vv_vehicleList.clear();
                        vv_vehicleList.addAll(searchTask.getValue());
                        vv_SwapField.clear();
                    });
                });

                searchTask.setOnFailed(e -> {
                    Platform.runLater(() -> {
                        ErrorHandler.ManageException(searchTask.getException());
                    });
                });

                Worker.submitTask(searchTask);
            } catch (NumberFormatException ex) {
                AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid Customer ID");
            }
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
    }

    @FXML
    private void showAllVehicles(ActionEvent event) {
        try{
            Task<List<Vehicle>> getAllVehicleTask = Vehicle_dao.getAllVehiclesTask();
            getAllVehicleTask.setOnSucceeded(e ->
            {
                Platform.runLater(() -> {
                    vv_vehicleList.clear();
                    vv_vehicleList.addAll(getAllVehicleTask.getValue());
                });
            });

            getAllVehicleTask.setOnFailed(e ->
            {
                Platform.runLater(() ->{
                    ErrorHandler.ManageException(getAllVehicleTask.getException());
                });
            });

            Worker.submitTask(getAllVehicleTask);

        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR,"Error", e.getMessage());
        }
    }

    private int currentVVFieldIdx = 0;
    @FXML
    private void handleVCSwapField(ActionEvent event)
    {
        currentVVFieldIdx +=1;
        if(currentVVFieldIdx == vvSwapFields.length)
            currentVVFieldIdx = 0;

        vv_SwapLabel.setText(vvSwapFields[currentVVFieldIdx].Text);
        vv_SwapField.setPromptText(vvSwapFields[currentVVFieldIdx].Prompt);
    }
}