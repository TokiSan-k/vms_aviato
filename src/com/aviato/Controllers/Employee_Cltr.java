package com.aviato.Controllers;

import com.aviato.Types.Employee; // Assuming an Employee entity class exists
import com.aviato.Types.Vehicle;
import com.aviato.Utils.AlertBox;
import com.aviato.Utils.ErrorHandler;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.db.dao.Employee_dao;
import com.aviato.db.dao.Vehicle_dao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import java.sql.Date;
import java.sql.SQLException;
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

    private final ObservableList<Employee> re_EmpList = FXCollections.observableArrayList();
    private final ObservableList<Employee> ve_EmpList = FXCollections.observableArrayList();

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
    private TableColumn<Employee, Long> re_empIdColumn;
    @FXML
    private TableColumn<Employee, String> re_empNameColumn;
    @FXML
    private TableColumn<Employee, String> re_positionColumn;
    @FXML
    private TableColumn<Employee, String> re_phoneColumn;
    @FXML
    private TableColumn<Employee, String> re_emailColumn;
    @FXML
    private TableColumn<Employee, Double> re_salaryColumn;
    @FXML
    private TableColumn<Employee, String> re_hireDateColumn; // Could use java.sql.Date if preferred
    @FXML
    private TableColumn<Employee, Double> re_hoursWorkedColumn;


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

    private Employee employee = new Employee();

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
        re_empIdColumn.setCellValueFactory(new PropertyValueFactory<>("empId"));
        re_empNameColumn.setCellValueFactory(new PropertyValueFactory<>("empName"));
        re_positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        re_phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        re_emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        re_salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        re_hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        re_hoursWorkedColumn.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));
        re_employeeTable.setItems(re_EmpList);

        // Set up View Employee table columns
        ve_empIdColumn.setCellValueFactory(new PropertyValueFactory<>("empId"));
        ve_empNameColumn.setCellValueFactory(new PropertyValueFactory<>("empName"));
        ve_positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        ve_phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ve_emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ve_salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ve_hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        ve_hoursWorkedColumn.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));
        ve_employeeTable.setItems(ve_EmpList);
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

    private void clearAddEmpFields()
    {
        ae_empNameField.clear();
        ae_positionField.clear();
        ae_phoneField.clear();
        ae_emailField.clear();
        ae_salaryField.clear();
        ae_hireDateField.clear();
        ae_hoursWorkedField.clear();
    }


    // Add Employee Submit Handler
    @FXML
    private void submitAddEmployee(ActionEvent event) {
        try {
            String empName = ae_empNameField.getText();
            String position = ae_positionField.getText();
            String phone = ae_phoneField.getText();
            String email = ae_emailField.getText();
            Double salary = Double.parseDouble(ae_salaryField.getText());
            Date hireDate = Date.valueOf(ae_hireDateField.getText());
            Double hoursWorked = Double.parseDouble(ae_hoursWorkedField.getText());

            employee.SetAllFields(empName, position, phone, email, salary, hireDate, hoursWorked);
            Task<Void> insertEmpTask = Employee_dao.insertEmployeeTask(employee);

            insertEmpTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearAddEmpFields();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Vehicle added successfully");
                });
            });

            insertEmpTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearAddEmpFields();
                    ErrorHandler.ManageException(insertEmpTask.getException());
                });
            });

            Worker.submitTask(insertEmpTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    // Remove Employee Event Handlers
    @FXML
    private void submitRemoveEmployee(ActionEvent event) {
        try {
            String empId = re_empIdField.getText();
            if (empId.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter a Employee ID to remove");
                return;
            }
            Long employeeId = Long.parseLong(empId);

            Task<Void> deleteTask = Employee_dao.deleteEmployeeTask(employeeId);
            deleteTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    re_empIdField.clear();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Employee removed successfully");
                });
            });

            deleteTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    re_empIdField.clear();
                    ErrorHandler.ManageException(deleteTask.getException());
                });
            });

            Worker.submitTask(deleteTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    @FXML
    private void searchRemoveEmployee(ActionEvent event) {
        try {
            String searchTerm = re_employeeSearchField.getText();
            if (searchTerm.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Search term cannot be empty!");
                return;
            }

            Task<List<Employee>> searchTask = Employee_dao.searchEmployeesByPartialNameTask(searchTerm);

            searchTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    re_EmpList.clear();
                    re_EmpList.addAll(searchTask.getValue());
                    re_employeeSearchField.clear();
                });
            });

            searchTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(searchTask.getException());
                });
            });

            Worker.submitTask(searchTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
    }

    @FXML
    private void searchAllRemoveEmployee(ActionEvent event) {
        try{
            Task<List<Employee>> getAllEmpTask = Employee_dao.getAllEmployeesTask();
            getAllEmpTask.setOnSucceeded(e ->
            {
                Platform.runLater(() -> {
                    re_EmpList.clear();
                    re_EmpList.addAll(getAllEmpTask.getValue());
                });
            });

            getAllEmpTask.setOnFailed(e ->
            {
                Platform.runLater(() ->{
                    ErrorHandler.ManageException(getAllEmpTask.getException());
                });
            });

            Worker.submitTask(getAllEmpTask);

        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR,"Error", e.getMessage());
        }
    }

    // Modify Employee Event Handlers
    private void OnMEEmployeeVerified(Employee emp)
    {
        employee.setEmpId(emp.getEmpId());
        me_empNameField.setText(emp.getEmpName());
        me_positionField.setText(emp.getPosition());
        me_phoneField.setText(emp.getPhone());
        me_emailField.setText(emp.getEmail());
        me_salaryField.setText(String.valueOf(emp.getSalary()));
        me_hireDateField.setText(emp.getHireDate().toString());
        me_hoursWorkedField.setText(String.valueOf(emp.getHoursWorked()));
    }

    @FXML
    private void verifyEmployeeId(ActionEvent event) {
        try {
            String empId = me_employeeIdField.getText();
            if (empId.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please Enter a Customer ID to remove");
                return;
            }
            Long employeeId = Long.parseLong(empId);

            Task<Employee> getEmpTask = Employee_dao.getEmployeeTask(employeeId);
            getEmpTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    //Verify UI update
                    OnMEEmployeeVerified(getEmpTask.getValue());
                    isMEVerified = true;
                    //For Now
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Employee ID Verified!");
                });
            });

            getEmpTask.setOnFailed(e -> {
                Platform.runLater(() ->{
                    ErrorHandler.ManageException(getEmpTask.getException());
                });
            });

            //getEmpTask.setOnFinished(e -> showLoading(false));
            Worker.submitTask(getEmpTask);
        }
        catch (Exception ex)
        {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    private boolean isMEVerified = false;
    @FXML
    private void editName(ActionEvent event) {
        if(isMEVerified) {
            me_empNameField.setEditable(true);
            me_empNameField.setDisable(false);
        }
    }

    @FXML
    private void editPosition(ActionEvent event) {
        if(isMEVerified) {
            me_positionField.setEditable(true);
            me_positionField.setDisable(false);
        }
    }

    @FXML
    private void editPhone(ActionEvent event) {
        if(isMEVerified) {
            me_phoneField.setEditable(true);
            me_phoneField.setDisable(false);
        }
    }

    @FXML
    private void editEmail(ActionEvent event) {
        if(isMEVerified) {
            me_emailField.setEditable(true);
            me_emailField.setDisable(false);
        }
    }

    @FXML
    private void editSalary(ActionEvent event) {
        if(isMEVerified) {
            me_salaryField.setEditable(true);
            me_salaryField.setDisable(false);
        }
    }

    @FXML
    private void editHireDate(ActionEvent event) {
        if(isMEVerified) {
            me_hireDateField.setEditable(true);
            me_hireDateField.setDisable(false);
        }
    }

    @FXML
    private void editHoursWorked(ActionEvent event) {
        if(isMEVerified) {
            me_hoursWorkedField.setEditable(true);
            me_hoursWorkedField.setDisable(false);
        }
    }

    private void ClearAllMEFields()
    {
        me_empNameField.clear();
        me_positionField.clear();
        me_phoneField.clear();
        me_emailField.clear();
        me_salaryField.clear();
        me_hireDateField.clear();
        me_hoursWorkedField.clear();
    }

    private void SetEditableMEFields(boolean status)
    {
        me_empNameField.setEditable(status);
        me_empNameField.setDisable(!status);

        me_positionField.setEditable(status);
        me_positionField.setDisable(!status);

        me_phoneField.setEditable(status);
        me_phoneField.setDisable(!status);

        me_salaryField.setEditable(status);
        me_salaryField.setDisable(!status);

        me_hireDateField.setEditable(status);
        me_hireDateField.setDisable(!status);

        me_hoursWorkedField.setEditable(status);
        me_hoursWorkedField.setDisable(!status);
    }

    @FXML
    private void submitModifyEmployee(ActionEvent event) {
        try {
            employee.setEmpId(Long.parseLong(me_employeeIdField.getText()));
            employee.SetAllFields(me_empNameField.getText(),
                    me_positionField.getText(),
                    me_phoneField.getText(),
                    me_emailField.getText(),
                    Double.parseDouble(me_salaryField.getText()),
                    Date.valueOf(me_hireDateField.getText()),
                    Double.parseDouble(me_hoursWorkedField.getText()));

            Task<Void> updateEmpTask = Employee_dao.updateEmployeeTask(employee);
            updateEmpTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ClearAllMEFields();
                    SetEditableMEFields(false);
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Employee Modified successfully");
                });
            });

            updateEmpTask.setOnFailed(e -> {
                Platform.runLater(() ->{
                    ClearAllMEFields();
                    SetEditableMEFields(false);
                    ErrorHandler.ManageException(updateEmpTask.getException());
                });
            });

            //getVehicleTask.setOnFinished(e -> showLoading(false));
            Worker.submitTask(updateEmpTask);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    // View Employee Event Handlers
    @FXML
    private void clearViewSearch(ActionEvent event) {
        ve_nameSearchField.clear();
        ve_employeeTable.getItems().clear();
    }

    @FXML
    private void searchViewEmployee(ActionEvent event) {
        try {
            String searchTerm = ve_nameSearchField.getText();
            if (searchTerm.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Search term cannot be empty!");
                return;
            }

            Task<List<Employee>> searchTask = Employee_dao.searchEmployeesByPartialNameTask(searchTerm);

            searchTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ve_EmpList.clear();
                    ve_EmpList.addAll(searchTask.getValue());
                    ve_nameSearchField.clear();
                });
            });

            searchTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(searchTask.getException());
                });
            });

            Worker.submitTask(searchTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
    }

    @FXML
    private void showAllEmployees(ActionEvent event) {
        try{
            Task<List<Employee>> getAllEmpTask = Employee_dao.getAllEmployeesTask();
            getAllEmpTask.setOnSucceeded(e ->
            {
                Platform.runLater(() -> {
                    ve_EmpList.clear();
                    ve_EmpList.addAll(getAllEmpTask.getValue());
                });
            });

            getAllEmpTask.setOnFailed(e ->
            {
                Platform.runLater(() ->{
                    ErrorHandler.ManageException(getAllEmpTask.getException());
                });
            });

            Worker.submitTask(getAllEmpTask);

        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR,"Error", e.getMessage());
        }
    }
}