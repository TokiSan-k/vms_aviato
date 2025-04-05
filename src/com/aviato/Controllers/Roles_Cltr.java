package com.aviato.Controllers;

import com.aviato.Types.RoleItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class Roles_Cltr {

    // Containers
    @FXML
    private VBox mainContainer;

    private VBox[] roleContainers = new VBox[4];
    private class RoleContainerEnum {
        public static String RoleContainerTag = "#roleContainer_";
        public static final byte AddRoleContainer = 0;
        public static final byte RemoveRoleContainer = 1;
        public static final byte UpdateRoleContainer = 2;
        public static final byte ViewRoleContainer = 3;
    }

    // Role Navbar
    @FXML
    private Button addRoleBtn;
    @FXML
    private Button removeRoleBtn;
    @FXML
    private Button updateRoleBtn;
    @FXML
    private Button viewRoleBtn;

    // Add Role Fields
    @FXML
    private TextField ar_usernameField;
    @FXML
    private TextField ar_passwordField;
    @FXML
    private TextField ar_emailField;
    @FXML
    private ComboBox<String> ar_roleNameComboBox;
    @FXML
    private Button ar_submitButton;
    @FXML
    private Button ar_cancelButton;

    // Remove Role Fields
    @FXML
    private TextField rr_employeeIdField;
    @FXML
    private Button rr_swapFieldButton;
    @FXML
    private Button rr_submitButton;
    @FXML
    private Button rr_cancelButton;

    // Update Role Fields
    @FXML
    private TextField ur_usernameField;
    @FXML
    private TextField ur_passwordField;
    @FXML
    private TextField ur_emailField;
    @FXML
    private ComboBox<String> ur_roleNameComboBox;
    @FXML
    private Button ur_submitButton;
    @FXML
    private Button ur_cancelButton;

    // View Role Fields
    @FXML
    private TextField vr_employeeIdSearchField;
    @FXML
    private Button vr_searchButton;
    @FXML
    private TableView<RoleItem> vr_RoleTable;
    @FXML
    private TableColumn<RoleItem, Integer> vr_EmployeeId;
    @FXML
    private TableColumn<RoleItem, String> vr_Username;
    @FXML
    private TableColumn<RoleItem, String> vr_Email;
    @FXML
    private TableColumn<RoleItem, String> vr_RoleName;

    // Initialize method to set up table columns and containers
    @FXML
    public void initialize() {
        for (byte i = 0; i < roleContainers.length; i++) {
            String container = RoleContainerEnum.RoleContainerTag + i;
            roleContainers[i] = (VBox) mainContainer.lookup(container);
        }
        roleContainers[RoleContainerEnum.AddRoleContainer].setVisible(true);
        roleContainers[RoleContainerEnum.AddRoleContainer].setManaged(true);

        // Populate role name combo boxes (example roles)
        ObservableList<String> roleNames = FXCollections.observableArrayList("Admin", "Manager", "Employee");
        ar_roleNameComboBox.setItems(roleNames);
        ur_roleNameComboBox.setItems(roleNames);

        // Set up View Role table columns
        vr_EmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        vr_Username.setCellValueFactory(new PropertyValueFactory<>("username"));
        vr_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        vr_RoleName.setCellValueFactory(new PropertyValueFactory<>("roleName"));
    }

    // Role NavBar
    private void TurnOffVisibleAndManageRoleContainer() {
        for (byte i = 0; i < roleContainers.length; i++) {
            roleContainers[i].setVisible(false);
            roleContainers[i].setManaged(false);
        }
    }

    @FXML
    private void handleRoleNavAddRole(ActionEvent event) {
        TurnOffVisibleAndManageRoleContainer();
        roleContainers[RoleContainerEnum.AddRoleContainer].setManaged(true);
        roleContainers[RoleContainerEnum.AddRoleContainer].setVisible(true);
    }

    @FXML
    private void handleRoleNavRemoveRole(ActionEvent event) {
        TurnOffVisibleAndManageRoleContainer();
        roleContainers[RoleContainerEnum.RemoveRoleContainer].setManaged(true);
        roleContainers[RoleContainerEnum.RemoveRoleContainer].setVisible(true);
    }

    @FXML
    private void handleRoleNavUpdateRole(ActionEvent event) {
        TurnOffVisibleAndManageRoleContainer();
        roleContainers[RoleContainerEnum.UpdateRoleContainer].setManaged(true);
        roleContainers[RoleContainerEnum.UpdateRoleContainer].setVisible(true);
    }

    @FXML
    private void handleRoleNavViewRole(ActionEvent event) {
        TurnOffVisibleAndManageRoleContainer();
        roleContainers[RoleContainerEnum.ViewRoleContainer].setManaged(true);
        roleContainers[RoleContainerEnum.ViewRoleContainer].setVisible(true);
    }

    // Add Role Submit Handler
    @FXML
    private void submitAddRole(ActionEvent event) {
        String username = ar_usernameField.getText();
        String password = ar_passwordField.getText();
        String email = ar_emailField.getText();
        String roleName = ar_roleNameComboBox.getValue();

        // EntityManager em = Persistence.createEntityManagerFactory("persistence-unit").createEntityManager();
        // em.getTransaction().begin();
        // Role role = new Role(username, password, email, roleName);
        // em.persist(role);
        // em.getTransaction().commit();

        // Clear fields
        ar_usernameField.clear();
        ar_passwordField.clear();
        ar_emailField.clear();
        ar_roleNameComboBox.setValue(null);
    }

    @FXML
    private void cancelAddRole(ActionEvent event) {
        ar_usernameField.clear();
        ar_passwordField.clear();
        ar_emailField.clear();
        ar_roleNameComboBox.setValue(null);
    }

    // Remove Role Event Handlers
    @FXML
    private void submitRemoveRole(ActionEvent event) {
        String employeeId = rr_employeeIdField.getText();
        // Add logic to remove the role by employeeId
        // Example: Use JPA to remove the role
        // EntityManager em = Persistence.createEntityManagerFactory("persistence-unit").createEntityManager();
        // em.getTransaction().begin();
        // Role role = em.find(Role.class, Integer.parseInt(employeeId));
        // if (role != null) em.remove(role);
        // em.getTransaction().commit();

        rr_employeeIdField.clear();
    }

    @FXML
    private void cancelRemoveRole(ActionEvent event) {
        rr_employeeIdField.clear();
    }

    @FXML
    private void swapRemoveField(ActionEvent event) {
        System.out.println("Swap field button clicked in Remove Role");
    }

    // Update Role Event Handlers
    @FXML
    private void submitUpdateRole(ActionEvent event) {
        String username = ur_usernameField.getText();
        String password = ur_passwordField.getText();
        String email = ur_emailField.getText();
        String roleName = ur_roleNameComboBox.getValue();

        // Add logic to update the role in the database
        // Example: Use JPA to update the role
        // EntityManager em = Persistence.createEntityManagerFactory("persistence-unit").createEntityManager();
        // em.getTransaction().begin();
        // Role role = em.find(Role.class, someEmployeeId);
        // if (role != null) {
        //     role.setUsername(username);
        //     role.setPassword(password);
        //     role.setEmail(email);
        //     role.setRoleName(roleName);
        // }
        // em.getTransaction().commit();

        ur_usernameField.clear();
        ur_passwordField.clear();
        ur_emailField.clear();
        ur_roleNameComboBox.setValue(null);
    }

    @FXML
    private void cancelUpdateRole(ActionEvent event) {
        ur_usernameField.clear();
        ur_passwordField.clear();
        ur_emailField.clear();
        ur_roleNameComboBox.setValue(null);
    }

    // View Role Event Handlers
    @FXML
    private void searchViewRole(ActionEvent event) {
        String employeeId = vr_employeeIdSearchField.getText();

        // EntityManager em = Persistence.createEntityManagerFactory("persistence-unit").createEntityManager();
        // Role role = em.find(Role.class, Integer.parseInt(employeeId));
        // if (role != null) {
        //     ObservableList<Role> roleList = FXCollections.observableArrayList(role);
        //     vr_RoleTable.setItems(roleList);
        // }
    }
}