package com.aviato.Controllers;

import com.aviato.Main;
import com.aviato.Types.Pages;
import com.aviato.Types.Service;
import com.aviato.Types.ServiceItem;
import com.aviato.Utils.AlertBox;
import com.aviato.Utils.ErrorHandler;
import com.aviato.Utils.Field;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.db.dao.Service_dao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Service_Cltr {
    // Containers
    @FXML
    private VBox mainContainer;

    private VBox[] serviceContainers = new VBox[5];
    private class SvcContainerEnum {
        public static String ServiceContainerTag = "#serviceContainer_";
        public static final byte AddServiceContainer = 0;
        public static final byte RemoveServiceContainer = 1;
        public static final byte ModifyServiceContainer = 2;
        public static final byte ViewServiceContainer = 3;
        public static final byte AddItemUsedContainer = 4;
    }

    private final ObservableList<Service> rs_SvcList = FXCollections.observableArrayList();
    private final ObservableList<Service> vs_SvcList = FXCollections.observableArrayList();
    private final ObservableList<ServiceItem> iu_ItemsList = FXCollections.observableArrayList();

    private final Field[] vs_ServiceSwapFields = { new Field(0,"Type:","Enter Service Type"),
            new Field(1,"ID:","Enter Service ID")};

    // Service Navbar
    @FXML private Button addSvcBtn;
    @FXML private Button removeSvcBtn;
    @FXML private Button modifySvcBtn;
    @FXML private Button viewSvcBtn;
    @FXML private Button addItemUsedBtn;

    // Add Service Fields
    @FXML private TextField as_serviceTypeField;
    @FXML private TextField as_serviceDateField;
    @FXML private TextField as_statusField;
    @FXML private TextField as_costField;
    @FXML private Button as_submitButton;

    // Remove Service Fields
    @FXML private TextField rs_serviceIdField;
    @FXML private Button rs_swapFieldButton;
    @FXML private Button rs_submitButton;
    @FXML private TextField rs_serviceSearchField;
    @FXML private Button rs_searchButton;
    @FXML private TableView<Service> rs_serviceTable;
    @FXML private TableColumn<Service, Long> serviceIdColumn;
    @FXML private TableColumn<Service, String> serviceTypeColumn;
    @FXML private TableColumn<Service, String> serviceStatusColumn;

    // Modify Service Fields
    @FXML private TextField ms_serviceIdField;
    @FXML private Button ms_verifyButton;
    @FXML private TextField ms_serviceTypeField;
    @FXML private TextField ms_serviceDateField;
    @FXML private TextField ms_statusField;
    @FXML private TextField ms_costField;
    @FXML private Button ms_editTypeButton;
    @FXML private Button ms_editDateButton;
    @FXML private Button ms_editStatusButton;
    @FXML private Button ms_editCostButton;
    @FXML private Button ms_submitButton;

    // View Service Fields
    @FXML private Text vs_typeLabel;
    @FXML private TextField vs_serviceSearchField;
    @FXML private Button vs_clearButton;
    @FXML private Button vs_searchButton;
    @FXML private Button vs_allServicesButton;
    @FXML private TableView<Service> vs_serviceTable;
    @FXML private TableColumn<Service, Long> vs_serviceIdColumn;
    @FXML private TableColumn<Service, String> vs_serviceTypeColumn;
    @FXML private TableColumn<Service, Date> vs_serviceDateColumn;
    @FXML private TableColumn<Service, String> vs_statusColumn;
    @FXML private TableColumn<Service, Double> vs_costColumn;

    // Items Used Fields
    @FXML private TextField iu_serviceIdField;
    @FXML private TextField iu_itemIdField;
    @FXML private TextField iu_quantityField;
    @FXML private Button iu_submitButton;
    @FXML private TableView<ServiceItem> iu_itemsTable;
    @FXML private TableColumn<ServiceItem, Long> iu_serviceIdColumn;
    @FXML private TableColumn<ServiceItem, Long> iu_itemIdColumn;
    @FXML private TableColumn<ServiceItem, Integer> iu_quantityUsedColumn;

    private Service service = new Service();

    @FXML
    private SideNavBar_Cltr Service_pageCltrController;

    @FXML
    private void handleMainMenu()
    {
        Main.currentStage.setScene(Pages.GetMainMenuScene(Main.GetRoleName()));
        Main.currentStage.setTitle("Main Menu");
    }

    @FXML
    public void initialize() {
        Service_pageCltrController.ApplyHighlight("Service_page");

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
        rs_serviceTable.setItems(rs_SvcList);

        // Set up View Service table columns
        vs_serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        vs_serviceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        vs_serviceDateColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDate"));
        vs_statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        vs_costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        vs_serviceTable.setItems(vs_SvcList);

        // Set up Items Used table columns
        iu_serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        iu_itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        iu_quantityUsedColumn.setCellValueFactory(new PropertyValueFactory<>("quantityUsed"));
        iu_itemsTable.setItems(iu_ItemsList);
    }

    // Service NavBar
    private void ResetUiComps()
    {
        turnOffVisibleAndManageSvcContainer();
        isMSVerified = false;
    }

    private void turnOffVisibleAndManageSvcContainer() {
        for (byte i = 0; i < serviceContainers.length; i++) {
            serviceContainers[i].setVisible(false);
            serviceContainers[i].setManaged(false);
        }
    }

    @FXML private void handleServiceNavAddSvc(ActionEvent event) {
        ResetUiComps();
        serviceContainers[SvcContainerEnum.AddServiceContainer].setManaged(true);
        serviceContainers[SvcContainerEnum.AddServiceContainer].setVisible(true);
    }

    @FXML private void handleServiceNavRemoveSvc(ActionEvent event) {
        ResetUiComps();
        serviceContainers[SvcContainerEnum.RemoveServiceContainer].setManaged(true);
        serviceContainers[SvcContainerEnum.RemoveServiceContainer].setVisible(true);
    }

    @FXML private void handleServiceNavModifySvc(ActionEvent event) {
        ResetUiComps();
        serviceContainers[SvcContainerEnum.ModifyServiceContainer].setManaged(true);
        serviceContainers[SvcContainerEnum.ModifyServiceContainer].setVisible(true);
    }

    @FXML private void handleServiceNavViewSvc(ActionEvent event) {
        ResetUiComps();
        serviceContainers[SvcContainerEnum.ViewServiceContainer].setManaged(true);
        serviceContainers[SvcContainerEnum.ViewServiceContainer].setVisible(true);
    }

    @FXML private void handleServiceNavItemsUsed(ActionEvent event) {
        ResetUiComps();
        serviceContainers[SvcContainerEnum.AddItemUsedContainer].setManaged(true);
        serviceContainers[SvcContainerEnum.AddItemUsedContainer].setVisible(true);
    }

    private void clearAddSvcFields() {
        as_serviceTypeField.clear();
        as_serviceDateField.clear();
        as_statusField.clear();
        as_costField.clear();
    }

    @FXML
    private void submitAddService(ActionEvent event) {
        try {
            String serviceType = as_serviceTypeField.getText();
            Date serviceDate = Date.valueOf(as_serviceDateField.getText());
            String status = as_statusField.getText();
            Double cost = Double.parseDouble(as_costField.getText());

            service = new Service(serviceType, serviceDate, status, cost);
            Task<Void> insertSvcTask = Service_dao.insertServiceTask(service);

            insertSvcTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearAddSvcFields();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Service added successfully");
                });
            });

            insertSvcTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearAddSvcFields();
                    ErrorHandler.ManageException(insertSvcTask.getException());
                });
            });

            Worker.submitTask(insertSvcTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    private void submitRemoveService(ActionEvent event) {
        try {
            String svcId = rs_serviceIdField.getText();
            if (svcId.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter a Service ID to remove");
                return;
            }
            Long serviceId = Long.parseLong(svcId);

            Task<Void> deleteTask = Service_dao.deleteServiceTask(serviceId);
            deleteTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    rs_serviceIdField.clear();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Service removed successfully");
                });
            });

            deleteTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    rs_serviceIdField.clear();
                    ErrorHandler.ManageException(deleteTask.getException());
                });
            });

            Worker.submitTask(deleteTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    private int rs_swapIdx = 0;
    @FXML
    private void swapRemoveField(ActionEvent event) {
        rs_swapIdx+=1;
        if(rs_swapIdx > 1)
            rs_swapIdx = 0;

        vs_typeLabel.setText(vs_ServiceSwapFields[rs_swapIdx].Text);
        vs_serviceSearchField.setPromptText(vs_ServiceSwapFields[rs_swapIdx].Prompt);
    }

    private void searchRSByServiceId(Long serviceId) throws Exception {
        try {
            Task<Service> searchSvcTask = Service_dao.getServiceTask(serviceId);
            searchSvcTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    rs_SvcList.clear();
                    rs_SvcList.addAll(searchSvcTask.getValue());
                });
            });

            searchSvcTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(searchSvcTask.getException());
                });
            });

            Worker.submitTask(searchSvcTask);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchRSServiceByType(String searchTerm) throws Exception
    {
        try {
            Task<List<Service>> searchSvcTask = Service_dao.searchServiceByTypeTask(searchTerm);
            searchSvcTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    rs_SvcList.clear();
                    rs_SvcList.addAll(searchSvcTask.getValue());
                });
            });

            searchSvcTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(searchSvcTask.getException());
                });
            });

            Worker.submitTask(searchSvcTask);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @FXML
    private void searchRemoveService(ActionEvent event) {
        String searchTerm = rs_serviceSearchField.getText();
        try {
            if(vs_swapIdx == 0){
                searchRSServiceByType(searchTerm);
            }
            else if(vs_swapIdx == 1) {
                Long serviceID = Long.parseLong(searchTerm);
                searchRSByServiceId(serviceID);
            }
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.INFORMATION,"Information", ex.getMessage());
        }
    }

    @FXML
    private void searchAllRemoveService(ActionEvent event) {
        try {
            Task<List<Service>> getAllSvcTask = Service_dao.getAllServicesTask();
            getAllSvcTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    rs_SvcList.clear();
                    rs_SvcList.addAll(getAllSvcTask.getValue());
                });
            });

            getAllSvcTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllSvcTask.getException());
                });
            });

            Worker.submitTask(getAllSvcTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void onMSServiceVerified(Service svc) {
        service.setServiceId(svc.getServiceId());
        ms_serviceTypeField.setText(svc.getServiceType());
        ms_serviceDateField.setText(svc.getServiceDate().toString());
        ms_statusField.setText(svc.getStatus());
        ms_costField.setText(String.valueOf(svc.getCost()));
    }

    @FXML
    private void verifyServiceId(ActionEvent event) {
        try {
            String svcId = ms_serviceIdField.getText();
            if (svcId.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter a Service ID to verify");
                return;
            }
            Long serviceId = Long.parseLong(svcId);

            Task<Service> getSvcTask = Service_dao.getServiceTask(serviceId);
            getSvcTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    onMSServiceVerified(getSvcTask.getValue());
                    isMSVerified = true;
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Service ID Verified!");
                });
            });

            getSvcTask.setOnFailed(e -> {
                Platform.runLater(() ->{
                    ErrorHandler.ManageException(getSvcTask.getException());
                });
            });

            Worker.submitTask(getSvcTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    private boolean isMSVerified = false;

    @FXML private void editType(ActionEvent event) {
        if (isMSVerified) {
            ms_serviceTypeField.setEditable(true);
            ms_serviceTypeField.setDisable(false);
        }
    }

    @FXML private void editDate(ActionEvent event) {
        if (isMSVerified) {
            ms_serviceDateField.setEditable(true);
            ms_serviceDateField.setDisable(false);
        }
    }

    @FXML private void editStatus(ActionEvent event) {
        if (isMSVerified) {
            ms_statusField.setEditable(true);
            ms_statusField.setDisable(false);
        }
    }

    @FXML private void editCost(ActionEvent event) {
        if (isMSVerified) {
            ms_costField.setEditable(true);
            ms_costField.setDisable(false);
        }
    }

    private void clearAllMSFields() {
        ms_serviceTypeField.clear();
        ms_serviceDateField.clear();
        ms_statusField.clear();
        ms_costField.clear();
    }

    private void setEditableMSFields(boolean status) {
        ms_serviceTypeField.setEditable(status);
        ms_serviceTypeField.setDisable(!status);
        ms_serviceDateField.setEditable(status);
        ms_serviceDateField.setDisable(!status);
        ms_statusField.setEditable(status);
        ms_statusField.setDisable(!status);
        ms_costField.setEditable(status);
        ms_costField.setDisable(!status);
    }

    @FXML
    private void submitModifyService(ActionEvent event) {
        try {
            service.setServiceId(Long.parseLong(ms_serviceIdField.getText()));
            service.setServiceType(ms_serviceTypeField.getText());
            service.setServiceDate(Date.valueOf(ms_serviceDateField.getText()));
            service.setStatus(ms_statusField.getText());
            service.setCost(Double.parseDouble(ms_costField.getText()));

            Task<Void> updateSvcTask = Service_dao.updateServiceTask(service);
            updateSvcTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearAllMSFields();
                    setEditableMSFields(false);
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Service Modified successfully");
                });
            });

            updateSvcTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearAllMSFields();
                    setEditableMSFields(false);
                    ErrorHandler.ManageException(updateSvcTask.getException());
                });
            });

            Worker.submitTask(updateSvcTask);
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    @FXML
    private void clearViewSearch(ActionEvent event) {
        vs_serviceSearchField.clear();
        vs_serviceTable.getItems().clear();
    }

    private int vs_swapIdx = 0;
    @FXML
    private void swapViewField(ActionEvent event) {
        vs_swapIdx+=1;
        if(vs_swapIdx > 1)
            vs_swapIdx = 0;

        vs_typeLabel.setText(vs_ServiceSwapFields[vs_swapIdx].Text);
        vs_serviceSearchField.setPromptText(vs_ServiceSwapFields[vs_swapIdx].Prompt);
    }

    @FXML
    private void searchViewService(ActionEvent event) {
        String searchTerm = vs_serviceSearchField.getText();
        try {
            if(vs_swapIdx == 0){
                searchServiceByType(searchTerm);
            }
            else if(vs_swapIdx == 1) {
                Long serviceID = Long.parseLong(searchTerm);
                searchByServiceId(serviceID);
            }
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.INFORMATION,"Information", ex.getMessage());
        }
    }

    private void searchByServiceId(Long serviceId) throws Exception {
        try {
            Task<Service> searchSvcTask = Service_dao.getServiceTask(serviceId);
            searchSvcTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    vs_SvcList.clear();
                    vs_SvcList.addAll(searchSvcTask.getValue());
                });
            });

            searchSvcTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(searchSvcTask.getException());
                });
            });

            Worker.submitTask(searchSvcTask);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void searchServiceByType(String searchTerm) throws Exception
    {
        try {
            Task<List<Service>> searchSvcTask = Service_dao.searchServiceByTypeTask(searchTerm);
            searchSvcTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    vs_SvcList.clear();
                    vs_SvcList.addAll(searchSvcTask.getValue());
                });
            });

            searchSvcTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(searchSvcTask.getException());
                });
            });

            Worker.submitTask(searchSvcTask);
        } catch (Exception ex) {
            throw ex;
        }
    }


    @FXML
    private void showAllServices(ActionEvent event) {
        try {
            Task<List<Service>> getAllSvcTask = Service_dao.getAllServicesTask();
            getAllSvcTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    vs_SvcList.clear();
                    vs_SvcList.addAll(getAllSvcTask.getValue());
                });
            });

            getAllSvcTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllSvcTask.getException());
                });
            });

            Worker.submitTask(getAllSvcTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void handleAddItemUsed(ActionEvent event) {
        try {
            Long serviceId = Long.parseLong(iu_serviceIdField.getText());
            Long itemId = Long.parseLong(iu_itemIdField.getText());
            Integer quantity = Integer.parseInt(iu_quantityField.getText());

            Task<Void> useInventoryTask = Service_dao.useInventoryTask(serviceId, itemId, quantity);
            useInventoryTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    iu_serviceIdField.clear();
                    iu_itemIdField.clear();
                    iu_quantityField.clear();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Item usage recorded successfully");
                    //refreshItemsUsedTable(serviceId);
                });
            });

            useInventoryTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    iu_serviceIdField.clear();
                    iu_itemIdField.clear();
                    iu_quantityField.clear();
                    ErrorHandler.ManageException(useInventoryTask.getException());
                });
            });

            Worker.submitTask(useInventoryTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    private void ShowAllItemsUsed(ActionEvent event)
    {
        try {

            Task<List<ServiceItem>> getInventoryTask = Service_dao.getAllServiceInventoryTask();
            getInventoryTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    iu_ItemsList.addAll(getInventoryTask.getValue());
                });
            });

            getInventoryTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getInventoryTask.getException());
                });
            });

            Worker.submitTask(getInventoryTask);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @FXML
    private void getAllItemsUsedTable(Long serviceId) throws Exception{
        try {
            Task<List<ServiceItem>> getInventoryTask = Service_dao.getServiceInventoryTask(serviceId);
            getInventoryTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    iu_ItemsList.addAll(getInventoryTask.getValue());
                });
            });

            getInventoryTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getInventoryTask.getException());
                });
            });

            Worker.submitTask(getInventoryTask);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}