package com.aviato.Controllers;

import com.aviato.Types.Service;
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
import java.sql.Date;
import java.util.List;

public class Service_Cltr {
    // Containers
    @FXML
    private VBox mainContainer;

    private VBox[] serviceContainers = new VBox[4];
    private class SvcContainerEnum {
        public static String ServiceContainerTag = "#serviceContainer_";
        public static final byte AddServiceContainer = 0;
        public static final byte RemoveServiceContainer = 1;
        public static final byte ModifyServiceContainer = 2;
        public static final byte ViewServiceContainer = 3;
    }

    // Service Navbar
    @FXML
    private Button addSvcBtn;
    @FXML
    private Button removeSvcBtn;
    @FXML
    private Button modifySvcBtn;
    @FXML
    private Button viewSvcBtn;

    // Add Service Fields
    @FXML
    private TextField as_serviceTypeField;
    @FXML
    private TextField as_serviceDateField;
    @FXML
    private TextField as_statusField;
    @FXML
    private TextField as_costField;
    @FXML
    private Button as_submitButton;

    // Remove Service Fields
    @FXML
    private TextField rs_serviceIdField;
    @FXML
    private Button rs_swapFieldButton;
    @FXML
    private Button rs_submitButton;
    @FXML
    private TextField rs_serviceSearchField;
    @FXML
    private Button rs_searchButton;
    @FXML
    private TableView<Service> rs_serviceTable;
    @FXML
    private TableColumn<Service, Long> serviceIdColumn;
    @FXML
    private TableColumn<Service, String> serviceTypeColumn;
    @FXML
    private TableColumn<Service, String> serviceStatusColumn;

    // Modify Service Fields
    @FXML
    private TextField ms_serviceIdField;
    @FXML
    private Button ms_verifyButton;
    @FXML
    private TextField ms_serviceTypeField;
    @FXML
    private TextField ms_serviceDateField;
    @FXML
    private TextField ms_statusField;
    @FXML
    private TextField ms_costField;
    @FXML
    private Button ms_editTypeButton;
    @FXML
    private Button ms_editDateButton;
    @FXML
    private Button ms_editStatusButton;
    @FXML
    private Button ms_editCostButton;
    @FXML
    private Button ms_submitButton;

    // View Service Fields
    @FXML
    private TextField vs_serviceSearchField;
    @FXML
    private Button vs_clearButton;
    @FXML
    private Button vs_searchButton;
    @FXML
    private Button vs_allServicesButton;
    @FXML
    private TableView<Service> vs_serviceTable;
    @FXML
    private TableColumn<Service, Long> vs_serviceIdColumn;
    @FXML
    private TableColumn<Service, String> vs_serviceTypeColumn;
    @FXML
    private TableColumn<Service, Date> vs_serviceDateColumn;
    @FXML
    private TableColumn<Service, String> vs_statusColumn;
    @FXML
    private TableColumn<Service, Double> vs_costColumn;

    // Initialize method to set up table columns
    @FXML
    public void initialize() {
        for (byte i = 0; i < serviceContainers.length; i++) {
            String container = SvcContainerEnum.ServiceContainerTag + i;
            serviceContainers[i] = (VBox) mainContainer.lookup(container);
        }
        serviceContainers[SvcContainerEnum.AddServiceContainer].setVisible(true);
        serviceContainers[SvcContainerEnum.AddServiceContainer].setManaged(true);

        // Set up Remove Service table columns
        serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        serviceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        serviceStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set up View Service table columns
        vs_serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        vs_serviceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        vs_serviceDateColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDate"));
        vs_statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        vs_costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    // Service NavBar
    private void turnOffVisibleAndManageSvcContainer() {
        for (byte i = 0; i < serviceContainers.length; i++) {
            serviceContainers[i].setVisible(false);
            serviceContainers[i].setManaged(false);
        }
    }

    @FXML
    private void handleServiceNavAddSvc(ActionEvent event) {
        turnOffVisibleAndManageSvcContainer();
        serviceContainers[SvcContainerEnum.AddServiceContainer].setManaged(true);
        serviceContainers[SvcContainerEnum.AddServiceContainer].setVisible(true);
    }

    @FXML
    private void handleServiceNavRemoveSvc(ActionEvent event) {
        turnOffVisibleAndManageSvcContainer();
        serviceContainers[SvcContainerEnum.RemoveServiceContainer].setManaged(true);
        serviceContainers[SvcContainerEnum.RemoveServiceContainer].setVisible(true);
    }

    @FXML
    private void handleServiceNavModifySvc(ActionEvent event) {
        turnOffVisibleAndManageSvcContainer();
        serviceContainers[SvcContainerEnum.ModifyServiceContainer].setManaged(true);
        serviceContainers[SvcContainerEnum.ModifyServiceContainer].setVisible(true);
    }

    @FXML
    private void handleServiceNavViewSvc(ActionEvent event) {
        turnOffVisibleAndManageSvcContainer();
        serviceContainers[SvcContainerEnum.ViewServiceContainer].setManaged(true);
        serviceContainers[SvcContainerEnum.ViewServiceContainer].setVisible(true);
    }

    // Add Service Submit Handler
    @FXML
    private void submitAddService(ActionEvent event) {

        // Clear fields
        as_serviceTypeField.clear();
        as_serviceDateField.clear();
        as_statusField.clear();
        as_costField.clear();
    }

    // Remove Service Event Handlers
    @FXML
    private void submitRemoveService(ActionEvent event) {

        rs_serviceIdField.clear();
    }

    @FXML
    private void swapRemoveField(ActionEvent event) {
        // Placeholder for swapping field (e.g., toggle between ID and another field)
        System.out.println("Swap field button clicked in Remove Service");
    }

    @FXML
    private void searchRemoveService(ActionEvent event) {
        // Placeholder for search logic (could use GetService with a filter)
        String searchTerm = rs_serviceSearchField.getText();
        System.out.println("Searching for service type: " + searchTerm);
    }

    // Modify Service Event Handlers
    @FXML
    private void verifyServiceId(ActionEvent event) {

    }

    @FXML
    private void editType(ActionEvent event) {
        ms_serviceTypeField.setEditable(true);
    }

    @FXML
    private void editDate(ActionEvent event) {
        ms_serviceDateField.setEditable(true);
    }

    @FXML
    private void editStatus(ActionEvent event) {
        ms_statusField.setEditable(true);
    }

    @FXML
    private void editCost(ActionEvent event) {
        ms_costField.setEditable(true);
    }

    @FXML
    private void submitModifyService(ActionEvent event) {

    }

    // View Service Event Handlers
    @FXML
    private void clearViewSearch(ActionEvent event) {
        vs_serviceSearchField.clear();
        vs_serviceTable.getItems().clear();
    }

    @FXML
    private void searchViewService(ActionEvent event) {
        // Placeholder for search logic (could filter GetAllServices results)
        String searchTerm = vs_serviceSearchField.getText();
        System.out.println("Searching for service type: " + searchTerm);
    }

    @FXML
    private void showAllServices(ActionEvent event) {

    }
}