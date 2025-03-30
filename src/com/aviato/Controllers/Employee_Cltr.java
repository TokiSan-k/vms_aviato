package com.aviato.Controllers;

import com.aviato.Types.Employee; // Assuming an Employee entity class exists
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

public class Employee_Cltr {
    // Containers
    @FXML
    private VBox mainContainer;

    private VBox[] employeeContainers = new VBox[4];
    private class EmpContainerEnum {
        public static String EmployeeContainerTag = "#employeeContainer_";
        public static final byte AddEmployeeContainer = 0;
        public static final byte RemoveEmployeeContainer = 1;
        public static final byte ModifyEmployeeContainer = 2;
        public static final byte ViewEmployeeContainer = 3;
    }

    // Employee Navbar
    @FXML
    private Button addEmpBtn;
    @FXML
    private Button removeEmpBtn;
    @FXML
    private Button modifyEmpBtn;
    @FXML
    private Button viewEmpBtn;

    // Add Employee Fields
    @FXML
    private TextField ae_empNameField;
    @FXML
    private TextField ae_positionField;
    @FXML
    private TextField ae_phoneField;
    @FXML
    private TextField ae_emailField;
    @FXML
    private TextField ae_salaryField;
    @FXML
    private TextField ae_hireDateField;
    @FXML
    private TextField ae_hoursWorkedField;
    @FXML
    private Button ae_submitButton;

    // Remove Employee Fields
    @FXML
    private TextField re_empIdField;
    @FXML
    private Button re_swapFieldButton;
    @FXML
    private Button re_submitButton;
    @FXML
    private TextField re_employeeSearchField;
    @FXML
    private Button re_searchButton;
    @FXML
    private TableView<Employee> re_employeeTable;
    @FXML
    private TableColumn<Employee, String> employeeNameColumn;
    @FXML
    private TableColumn<Employee, String> employeePositionColumn;
    @FXML
    private TableColumn<Employee, String> employeePhoneColumn;

    // Modify Employee Fields
    @FXML
    private TextField me_employeeIdField;
    @FXML
    private Button me_verifyButton;
    @FXML
    private TextField me_empNameField;
    @FXML
    private TextField me_positionField;
    @FXML
    private TextField me_phoneField;
    @FXML
    private TextField me_emailField;
    @FXML
    private TextField me_salaryField;
    @FXML
    private TextField me_hireDateField;
    @FXML
    private TextField me_hoursWorkedField;
    @FXML
    private Button me_editNameButton;
    @FXML
    private Button me_editPositionButton;
    @FXML
    private Button me_editPhoneButton;
    @FXML
    private Button me_editEmailButton;
    @FXML
    private Button me_editSalaryButton;
    @FXML
    private Button me_editHireDateButton;
    @FXML
    private Button me_editHoursWorkedButton;
    @FXML
    private Button me_submitButton;

    // View Employee Fields
    @FXML
    private TextField ve_nameSearchField;
    @FXML
    private Button ve_clearButton;
    @FXML
    private Button ve_searchButton;
    @FXML
    private Button ve_allEmployeesButton;
    @FXML
    private TableView<Employee> ve_employeeTable;
    @FXML
    private TableColumn<Employee, Long> ve_empIdColumn;
    @FXML
    private TableColumn<Employee, String> ve_empNameColumn;
    @FXML
    private TableColumn<Employee, String> ve_positionColumn;
    @FXML
    private TableColumn<Employee, String> ve_phoneColumn;
    @FXML
    private TableColumn<Employee, String> ve_emailColumn;
    @FXML
    private TableColumn<Employee, Double> ve_salaryColumn;
    @FXML
    private TableColumn<Employee, String> ve_hireDateColumn; // Could use java.sql.Date if preferred
    @FXML
    private TableColumn<Employee, Double> ve_hoursWorkedColumn;

    // Initialize method to set up table columns
    @FXML
    public void initialize() {
        for (byte i = 0; i < employeeContainers.length; i++)
        {
            String container = EmpContainerEnum.EmployeeContainerTag + i;
            employeeContainers[i] = (VBox) mainContainer.lookup(container);
        }
        employeeContainers[EmpContainerEnum.AddEmployeeContainer].setVisible(true);
        employeeContainers[EmpContainerEnum.AddEmployeeContainer].setManaged(true);

        // Set up Remove Employee table columns
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("empName"));
        employeePositionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        employeePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Set up View Employee table columns
        ve_empIdColumn.setCellValueFactory(new PropertyValueFactory<>("empId"));
        ve_empNameColumn.setCellValueFactory(new PropertyValueFactory<>("empName"));
        ve_positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        ve_phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ve_emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ve_salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ve_hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        ve_hoursWorkedColumn.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));
    }

    // Employee NavBar
    private void turnOffVisibleAndManageEmpContainer() {
        for (byte i = 0; i < employeeContainers.length; i++) {
            employeeContainers[i].setVisible(false);
            employeeContainers[i].setManaged(false);
        }
    }

    @FXML
    private void handleEmployeeNavAddEmp(ActionEvent event) {
        turnOffVisibleAndManageEmpContainer();
        employeeContainers[EmpContainerEnum.AddEmployeeContainer].setManaged(true);
        employeeContainers[EmpContainerEnum.AddEmployeeContainer].setVisible(true);
    }

    @FXML
    private void handleEmployeeNavRemoveEmp(ActionEvent event) {
        turnOffVisibleAndManageEmpContainer();
        employeeContainers[EmpContainerEnum.RemoveEmployeeContainer].setManaged(true);
        employeeContainers[EmpContainerEnum.RemoveEmployeeContainer].setVisible(true);
    }

    @FXML
    private void handleEmployeeNavModifyEmp(ActionEvent event) {
        turnOffVisibleAndManageEmpContainer();
        employeeContainers[EmpContainerEnum.ModifyEmployeeContainer].setManaged(true);
        employeeContainers[EmpContainerEnum.ModifyEmployeeContainer].setVisible(true);
    }

    @FXML
    private void handleEmployeeNavViewEmp(ActionEvent event) {
        turnOffVisibleAndManageEmpContainer();
        employeeContainers[EmpContainerEnum.ViewEmployeeContainer].setManaged(true);
        employeeContainers[EmpContainerEnum.ViewEmployeeContainer].setVisible(true);
    }

    // Add Employee Submit Handler
    @FXML
    private void submitAddEmployee(ActionEvent event) {
        String empName = ae_empNameField.getText();
        String position = ae_positionField.getText();
        String phone = ae_phoneField.getText();
        String email = ae_emailField.getText();
        String salary = ae_salaryField.getText();
        String hireDate = ae_hireDateField.getText();
        String hoursWorked = ae_hoursWorkedField.getText();

        // Clear fields
        ae_empNameField.clear();
        ae_positionField.clear();
        ae_phoneField.clear();
        ae_emailField.clear();
        ae_salaryField.clear();
        ae_hireDateField.clear();
        ae_hoursWorkedField.clear();
    }

    // Remove Employee Event Handlers
    @FXML
    private void submitRemoveEmployee(ActionEvent event) {
        String empId = re_empIdField.getText();
        // Add database removal logic here
    }

    @FXML
    private void swapRemoveField(ActionEvent event) {
        // Placeholder for swapping field (e.g., toggle between ID and another field)
        System.out.println("Swap field button clicked in Remove Employee");
    }

    @FXML
    private void searchRemoveEmployee(ActionEvent event) {
        String searchTerm = re_employeeSearchField.getText();
        // Add search logic here
    }

    // Modify Employee Event Handlers
    @FXML
    private void verifyEmployeeId(ActionEvent event) {
        String empId = me_employeeIdField.getText();
        // Add verification logic here (e.g., fetch employee details and populate fields)
    }

    @FXML
    private void editName(ActionEvent event) {
        me_empNameField.setEditable(true);
    }

    @FXML
    private void editPosition(ActionEvent event) {
        me_positionField.setEditable(true);
    }

    @FXML
    private void editPhone(ActionEvent event) {
        me_phoneField.setEditable(true);
    }

    @FXML
    private void editEmail(ActionEvent event) {
        me_emailField.setEditable(true);
    }

    @FXML
    private void editSalary(ActionEvent event) {
        me_salaryField.setEditable(true);
    }

    @FXML
    private void editHireDate(ActionEvent event) {
        me_hireDateField.setEditable(true);
    }

    @FXML
    private void editHoursWorked(ActionEvent event) {
        me_hoursWorkedField.setEditable(true);
    }

    @FXML
    private void submitModifyEmployee(ActionEvent event) {
        // Add database update logic here
    }

    // View Employee Event Handlers
    @FXML
    private void clearViewSearch(ActionEvent event) {
        ve_nameSearchField.clear();
        ve_employeeTable.getItems().clear();
    }

    @FXML
    private void searchViewEmployee(ActionEvent event) {
        String searchTerm = ve_nameSearchField.getText();
        // Add search logic here
    }

    @FXML
    private void showAllEmployees(ActionEvent event) {
        // Add logic to fetch and display all employees
    }
}