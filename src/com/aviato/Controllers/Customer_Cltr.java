package com.aviato.Controllers;

import com.aviato.Types.Customer;
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

public class Customer_Cltr
{
    //Containers
    @FXML
    private VBox mainContainer;

    private VBox[] customerContainers = new VBox[4];
    private class CustContainerEnum
    {
        public static String CustomerContainerTag = "#customerContainer_";
        public static final byte AddCustomerContainer = 0;
        public static final byte RemoveCustomerContainer = 1;
        public static final byte ModifyCustomerContainer = 2;
        public static final byte ViewCustomerContainer = 3;
    }

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
    private TextField ac_lastNameField;
    @FXML
    private TextField ac_emailField;
    @FXML
    private TextField ac_phoneField;
    @FXML
    private TextField ac_addressField;

    // Remove Customer Fields
    @FXML
    private TextField rc_emailField;
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
    private TextField rc_customerIdField;
    @FXML
    private Button rc_verifyButton;
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
    private TextField vc_nameSearchField;
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
    private TableColumn<Customer, String> vc_lastNameColumn;
    @FXML
    private TableColumn<Customer, String> vc_emailColumn;
    @FXML
    private TableColumn<Customer, String> vc_phoneColumn;
    @FXML
    private TableColumn<Customer, String> vc_addressColumn;

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
        vc_firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        vc_lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        vc_emailColumn.setCellValueFactory(new PropertyValueFactory<>("EmailId"));
        vc_phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        vc_addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
    }

    //Customer NavBar
    private void TurnOffVisibleAndManageCustContainer()
    {
        for(byte i =0; i<customerContainers.length; i++)
        {
            customerContainers[i].setVisible(false);
            customerContainers[i].setManaged(false);
        }
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

    // Add Customer Submit Handler
    @FXML
    private void submitAddCustomer(ActionEvent event) {
        String firstName = ac_firstNameField.getText();
        String lastName = ac_lastNameField.getText();
        String fullName = firstName + " " + lastName; // Combine for cust_name
        String email = ac_emailField.getText();
        String phone = ac_phoneField.getText();
        String address = ac_addressField.getText();

        // Clear fields
        ac_firstNameField.clear();
        ac_lastNameField.clear();
        ac_emailField.clear();
        ac_phoneField.clear();
        ac_addressField.clear();
    }

    // Remove Customer Event Handlers
    @FXML
    private void submitRemoveCustomer(ActionEvent event) {

    }

    @FXML
    private void swapRemoveField(ActionEvent event) {
        // Placeholder for swapping field (e.g., toggle between email and ID input)
        System.out.println("Swap field button clicked in Remove Customer");
    }

    @FXML
    private void searchRemoveCustomer(ActionEvent event) {
        String searchTerm = rc_customerSearchField.getText();
    }

    // Modify Customer Event Handlers
    @FXML
    private void verifyCustomerId(ActionEvent event) {

    }

    @FXML
    private void editFirstName(ActionEvent event) {
        mc_firstNameField.setEditable(true);
    }

    @FXML
    private void editLastName(ActionEvent event) {
        mc_lastNameField.setEditable(true);
    }

    @FXML
    private void editEmail(ActionEvent event) {
        mc_emailField.setEditable(true);
    }

    @FXML
    private void editPhone(ActionEvent event) {
        mc_phoneField.setEditable(true);
    }

    @FXML
    private void editAddress(ActionEvent event) {
        mc_addressField.setEditable(true);
    }

    @FXML
    private void submitModifyCustomer(ActionEvent event) {

    }

    // View Customer Event Handlers
    @FXML
    private void clearViewSearch(ActionEvent event) {
        vc_nameSearchField.clear();
        vc_customerTable.getItems().clear();
    }

    @FXML
    private void searchViewCustomer(ActionEvent event) {
        // Note: Stored procedure GetCustomer requires cust_id, not name search
        // For name-based search, you'd need a different approach or stored proc

    }

    @FXML
    private void showAllCustomers(ActionEvent event) {
        // Note: GetAllCustomer seems identical to GetCustomer; assuming it fetches all if p_cust_id is null

    }
}
