package com.aviato.Controllers;

import com.aviato.Main;
import com.aviato.Utils.AlertBox;

import com.aviato.Types.Customer;
import com.aviato.Types.Pages;
import com.aviato.Utils.ErrorHandler;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.Utils.Field;
import com.aviato.db.dao.Customer_dao;
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
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Customer_Cltr
{
    //Containers
    @FXML
    private VBox mainContainer;

    private final ObservableList<Customer> vc_customerList = FXCollections.observableArrayList();

    private VBox[] customerContainers = new VBox[4];
    private class CustContainerEnum
    {
        public static String CustomerContainerTag = "#customerContainer_";
        public static final byte AddCustomerContainer = 0;
        public static final byte RemoveCustomerContainer = 1;
        public static final byte ModifyCustomerContainer = 2;
        public static final byte ViewCustomerContainer = 3;
    }

    private final Field[] rcSwapFields = { new Field(0,"ID:","Enter Customer ID"),
            new Field(1,"Email:", "Enter Email ID")};

    private final Field[] vcSwapFields = { new Field(0,"ID:","Enter Customer ID"),
            new Field(1,"Name:","Enter Customer Name")};

    private final int TotalModifyFields = 5;

    //Customer Navbar
    @FXML
    private Button addCustBtn;
    @FXML
    private Button removeCustBtn;
    @FXML
    private Button modifyCustBtn;
    @FXML
    private Button viewCustBtn;

    // Add Customer Fields
    @FXML
    private TextField ac_firstNameField;
    @FXML
    private TextField ac_LastNameField;
    @FXML
    private TextField ac_emailField;
    @FXML
    private TextField ac_phoneField;
    @FXML
    private TextField ac_addressField;

    // Remove Customer Fields
    @FXML
    private TextField rc_SwapField;
    @FXML
    private Text rc_SwapLabel;
    @FXML
    private Button rc_swapFieldButton;
    @FXML
    private TextField rc_customerSearchField;
    @FXML
    private Button rc_searchButton;
    @FXML
    private TableView<Customer> rc_customerTable;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> customerEmailColumn;
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;

    // Modify Customer Fields
    @FXML
    private TextField mc_customerIdField;
    @FXML
    private Button mc_verifyButton;
    @FXML
    private TextField mc_firstNameField;
    @FXML
    private TextField mc_lastNameField;
    @FXML
    private TextField mc_emailField;
    @FXML
    private TextField mc_phoneField;
    @FXML
    private TextField mc_addressField;
    @FXML
    private Button mc_editFirstNameButton;
    @FXML
    private Button mc_editLastNameButton;
    @FXML
    private Button mc_editEmailButton;
    @FXML
    private Button mc_editPhoneNoButton;
    @FXML
    private Button mc_editAddressButton;

    // View Customer Fields
    @FXML
    private TextField vc_swapField;
    @FXML
    private Text vc_swapLabel;
    @FXML
    private Button vc_clearButton;
    @FXML
    private Button vc_searchButton;
    @FXML
    private Button vc_allCustomersButton;
    @FXML
    private TableView<Customer> vc_customerTable;
    @FXML
    private TableColumn<Customer, String> vc_firstNameColumn;
    @FXML
    private TableColumn<Customer, String> vc_IdColumn;
    @FXML
    private TableColumn<Customer, String> vc_emailColumn;
    @FXML
    private TableColumn<Customer, String> vc_phoneColumn;
    @FXML
    private TableColumn<Customer, String> vc_addressColumn;

    private Customer customer = new Customer();


    //ToDo: For internal Nav check if clicked on same button results in no Change.

    // Initialize method to set up table columns
    @FXML
    public void initialize() {

        for(byte i =0; i<customerContainers.length; i++)
        {
            String container = CustContainerEnum.CustomerContainerTag + i;
            customerContainers[i] = (VBox)mainContainer.lookup(container);
        }
        customerContainers[CustContainerEnum.AddCustomerContainer].setVisible(true);
        customerContainers[CustContainerEnum.AddCustomerContainer].setManaged(true);

        // Set up Remove Customer table columns
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("EmailId"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));

        // Set up View Customer table columns
        vc_IdColumn.setCellValueFactory(new PropertyValueFactory<>("custId"));
        vc_firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        vc_emailColumn.setCellValueFactory(new PropertyValueFactory<>("EmailId"));
        vc_phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        vc_addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));


        vc_customerTable.setItems(vc_customerList);
    }

    //Customer NavBar
    private void TurnOffVisibleAndManageCustContainer()
    {
        for(byte i =0; i<customerContainers.length; i++)
        {
            customerContainers[i].setVisible(false);
            customerContainers[i].setManaged(false);
        }

        SetEditableMCFields(false);
        vc_customerList.clear();
    }

    @FXML
    private void handleCustomerNavAddCust(ActionEvent event) {
        TurnOffVisibleAndManageCustContainer();
        customerContainers[CustContainerEnum.AddCustomerContainer].setManaged(true);
        customerContainers[CustContainerEnum.AddCustomerContainer].setVisible(true);
    }

    @FXML
    private void handleCustomerNavRemoveCust(ActionEvent event) {
        TurnOffVisibleAndManageCustContainer();
        customerContainers[CustContainerEnum.RemoveCustomerContainer].setManaged(true);
        customerContainers[CustContainerEnum.RemoveCustomerContainer].setVisible(true);
    }

    @FXML
    private void handleCustomerNavModifyCust(ActionEvent event) {
        TurnOffVisibleAndManageCustContainer();
        customerContainers[CustContainerEnum.ModifyCustomerContainer].setManaged(true);
        customerContainers[CustContainerEnum.ModifyCustomerContainer].setVisible(true);
    }

    @FXML
    private void handleCustomerNavViewCust(ActionEvent event) {
        TurnOffVisibleAndManageCustContainer();
        customerContainers[CustContainerEnum.ViewCustomerContainer].setManaged(true);
        customerContainers[CustContainerEnum.ViewCustomerContainer].setVisible(true);
    }

    @FXML
    private void handleMainMenuButton(ActionEvent event)
    {
        Main.currentStage.setScene(Pages.GetMainMenuScene(Main.GetRoleName()));
    }

    private int currentRCFieldIdx = 0;
    @FXML
    private void handleRCSwapField(ActionEvent event)
    {
        currentRCFieldIdx +=1;
        if(currentRCFieldIdx == rcSwapFields.length)
            currentRCFieldIdx = 0;

        rc_SwapLabel.setText(rcSwapFields[currentRCFieldIdx].Text);
        rc_SwapField.setPromptText(rcSwapFields[currentRCFieldIdx].Prompt);
    }

    private void ClearAddCustFields()
    {
        ac_firstNameField.clear();
        ac_LastNameField.clear();
        ac_emailField.clear();
        ac_phoneField.clear();
        ac_addressField.clear();
    }

    // Add Customer Submit Handler
    @FXML
    private void submitAddCustomer(ActionEvent event) {
        try {
            String firstName = ac_firstNameField.getText();
            String lastName = ac_LastNameField.getText();
            String fullName = firstName + " " + lastName;
            String email = ac_emailField.getText();
            String phone = ac_phoneField.getText();
            String address = ac_addressField.getText();

            customer.SetAllFields(fullName, lastName, phone, email, address);
            Task<Void> insertTask = Customer_dao.insertCustomerTask(customer);

            insertTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ClearAddCustFields();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Customer added successfully");
                });
            });

            insertTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ClearAddCustFields();
                    ErrorHandler.ManageException(insertTask.getException());
                });
            });

            Worker.submitTask(insertTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    // Remove Customer Event Handlers
    @FXML
    private void submitRemoveCustomer(ActionEvent event) {
        try {
            String inputID = rc_SwapField.getText();
            if (inputID.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please Enter a Customer ID to remove");
                return;
            }
            Long custId = Long.parseLong(inputID);

            Task<Void> deleteTask = Customer_dao.deleteCustomerTask(custId);
            deleteTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    rc_SwapField.clear();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Customer removed successfully");
                });
            });

            deleteTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    rc_SwapField.clear();
                    ErrorHandler.ManageException(deleteTask.getException());
                });
            });

            Worker.submitTask(deleteTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    @FXML
    private void searchNameRCTable(ActionEvent event) {
        String searchTerm = rc_customerSearchField.getText();

    }

    // Modify Customer Event Handlers
    @FXML
    private void MCverifyCustomerId(ActionEvent event) {
        try {
            String inputID = mc_customerIdField.getText();
            if (inputID.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please Enter a Customer ID to remove");
                return;
            }
            Long custId = Long.parseLong(inputID);

            Task<Customer> getCustTask = Customer_dao.getCustomerTask(custId);
            getCustTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    //Verify UI update
                    OnCustomerVerified(getCustTask.getValue());
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Customer removed successfully");
                });
            });

            getCustTask.setOnFailed(e -> {
                Platform.runLater(() -> {
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

    private void OnCustomerVerified(Customer customer)
    {
        String[] nameParts = customer.getName().split(" ");
        String FName = nameParts[0];
        String LName = String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length));
        mc_firstNameField.setText(FName);
        mc_lastNameField.setText(LName);
        mc_emailField.setText(customer.getEmailId());
        mc_phoneField.setText(customer.getPhone());
        mc_addressField.setText(customer.getAddress());

        //UI
    }

    private void SetEditableMCFields(boolean status)
    {
        mc_firstNameField.setEditable(status);
        mc_firstNameField.setDisable(!status);

        mc_lastNameField.setEditable(status);
        mc_lastNameField.setDisable(!status);

        mc_emailField.setEditable(status);
        mc_emailField.setDisable(!status);

        mc_phoneField.setEditable(status);
        mc_phoneField.setDisable(!status);

        mc_addressField.setEditable(status);
        mc_addressField.setDisable(!status);
    }


    @FXML
    private void editFirstName(ActionEvent event) {
        mc_firstNameField.setEditable(true);
        mc_firstNameField.setDisable(false);
    }

    @FXML
    private void editLastName(ActionEvent event) {
        mc_lastNameField.setEditable(true);
        mc_lastNameField.setDisable(false);
    }

    @FXML
    private void editEmail(ActionEvent event) {
        mc_emailField.setEditable(true);
        mc_emailField.setDisable(false);
    }

    @FXML
    private void editPhone(ActionEvent event) {
        mc_phoneField.setEditable(true);
        mc_phoneField.setDisable(false);
    }

    @FXML
    private void editAddress(ActionEvent event) {
        mc_addressField.setEditable(true);
        mc_addressField.setDisable(false);
    }

    private void ClearAllMCFields()
    {
        mc_customerIdField.clear();
        mc_firstNameField.clear();
        mc_lastNameField.clear();
        mc_emailField.clear();
        mc_phoneField.clear();
        mc_addressField.clear();
    }


    @FXML
    private void submitModifyCustomer(ActionEvent event) {
        try {
            customer.setCustId(Long.parseLong(mc_customerIdField.getText()));
            customer.SetAllFields(mc_firstNameField.getText(),
                    mc_lastNameField.getText(),
                    mc_phoneField.getText(),
                    mc_emailField.getText(),
                    mc_addressField.getText());

            Task<Void> updateCustTask = Customer_dao.updateCustomerTask(customer);
            updateCustTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ClearAllMCFields();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Customer modified successfully");
                });
            });

            updateCustTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ClearAllMCFields();
                    ErrorHandler.ManageException(updateCustTask.getException());
                });
            });

            Worker.submitTask(updateCustTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    @FXML
    private void searchViewCustomer(ActionEvent event) {
        try {
            String vcId = vc_swapField.getText();
            if(vcId.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.ERROR,"Error", "Search ID cannot be empty!");
            }
            Long custId = Long.parseLong(vcId);

            Task<Customer> getVCustTask = Customer_dao.getCustomerTask(custId);
            getVCustTask.setOnSucceeded(e ->
            {
                Platform.runLater(() -> {
                    vc_customerList.clear();
                    vc_customerList.addAll(getVCustTask.getValue());
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Customer Modified successfully");
                });
            });

            getVCustTask.setOnFailed(e ->
            {
                Platform.runLater(() ->{
                    ErrorHandler.ManageException(getVCustTask.getException());
                });
            });
            Worker.submitTask(getVCustTask);
        }
        catch (Exception ex)
        {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
    }

    @FXML
    private void showAllCustomers(ActionEvent event) {
        try {
            Task<List<Customer>> getAllCustTask = Customer_dao.getAllCustomersTask();
            getAllCustTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    vc_customerList.clear();
                    vc_customerList.addAll(getAllCustTask.getValue());
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "All customers retrieved successfully");
                });
            });

            getAllCustTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllCustTask.getException());
                });
            });

            Worker.submitTask(getAllCustTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    private int currentVCFieldIdx = 0;
    @FXML
    private void handleVCSwapField(ActionEvent event)
    {
        currentVCFieldIdx +=1;
        if(currentVCFieldIdx == vcSwapFields.length)
            currentVCFieldIdx = 0;

        vc_swapLabel.setText(vcSwapFields[currentVCFieldIdx].Text);
        vc_swapField.setPromptText(vcSwapFields[currentVCFieldIdx].Prompt);
    }
}