package com.aviato.Controllers;

import com.aviato.Main;
import com.aviato.Types.Pages;
import com.aviato.Types.User;
import com.aviato.Types.Policy;
import com.aviato.Utils.AlertBox;
import com.aviato.Utils.ErrorHandler;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.db.dao.User_dao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Date;
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

    private final ObservableList<User> vr_UserList = FXCollections.observableArrayList();

    // Role Navbar
    @FXML private Button addRoleBtn;
    @FXML private Button removeRoleBtn;
    @FXML private Button updateRoleBtn;
    @FXML private Button viewRoleBtn;

    // Add Role Fields
    @FXML private TextField ar_usernameField;
    @FXML private TextField ar_passwordField;
    @FXML private TextField ar_emailField;
    @FXML private ComboBox<String> ar_roleNameComboBox;  // Using as roleId
    @FXML private Button ar_submitButton;

    // Remove Role Fields
    @FXML private TextField rr_userEmailIdField;  // Using email instead of employeeId
    @FXML private Button rr_submitButton;

    // Update Role Fields
    @FXML private TextField ru_swapField;
    @FXML private TextField ur_usernameField;
    @FXML private TextField ur_passwordField;
    @FXML private TextField ur_emailField;
    @FXML private ComboBox<String> ur_roleNameComboBox;
    @FXML private Button ur_submitButton;

    // View Role Fields
    @FXML private TextField vr_employeeIdSearchField;
    @FXML private Button vr_searchButton;
    @FXML private TableView<User> vr_RoleTable;
    @FXML private TableColumn<User, Long> vr_UserID;
    @FXML private TableColumn<User, String> vr_Username;
    @FXML private TableColumn<User, String> vr_Email;
    @FXML private TableColumn<User, Long> vr_RoleName;

    private User user = new User();
    private Long currentUserId = null;  // For update functionality

    @FXML
    private SideNavBar_Cltr User_pageCltrController;

    @FXML
    private void handleMainMenu()
    {
        Main.currentStage.setScene(Pages.GetMainMenuScene(Main.GetRoleName()));
        Main.currentStage.setTitle("Main Menu");
    }

    @FXML
    public void initialize() {
        User_pageCltrController.ApplyHighlight("User_page");

        for (byte i = 0; i < roleContainers.length; i++) {
            String container = RoleContainerEnum.RoleContainerTag + i;
            roleContainers[i] = (VBox) mainContainer.lookup(container);
        }
        roleContainers[RoleContainerEnum.AddRoleContainer].setVisible(true);
        roleContainers[RoleContainerEnum.AddRoleContainer].setManaged(true);

        ar_roleNameComboBox.setItems(Policy.roleNames);
        ur_roleNameComboBox.setItems(Policy.roleNames);

        // Set up View Role table columns
        vr_UserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        vr_Username.setCellValueFactory(new PropertyValueFactory<>("username"));
        vr_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        vr_RoleName.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        vr_RoleTable.setItems(vr_UserList);
    }

    // Role NavBar
    private void resetUiComps() {
        turnOffVisibleAndManageRoleContainer();
        currentUserId = null;
    }

    private void turnOffVisibleAndManageRoleContainer() {
        for (byte i = 0; i < roleContainers.length; i++) {
            roleContainers[i].setVisible(false);
            roleContainers[i].setManaged(false);
        }
    }

    @FXML private void handleRoleNavAddRole(ActionEvent event) {
        resetUiComps();
        roleContainers[RoleContainerEnum.AddRoleContainer].setManaged(true);
        roleContainers[RoleContainerEnum.AddRoleContainer].setVisible(true);
    }

    @FXML private void handleRoleNavRemoveRole(ActionEvent event) {
        resetUiComps();
        roleContainers[RoleContainerEnum.RemoveRoleContainer].setManaged(true);
        roleContainers[RoleContainerEnum.RemoveRoleContainer].setVisible(true);
    }

    @FXML private void handleRoleNavUpdateRole(ActionEvent event) {
        resetUiComps();
        roleContainers[RoleContainerEnum.UpdateRoleContainer].setManaged(true);
        roleContainers[RoleContainerEnum.UpdateRoleContainer].setVisible(true);
    }

    @FXML private void handleRoleNavViewRole(ActionEvent event) {
        resetUiComps();
        roleContainers[RoleContainerEnum.ViewRoleContainer].setManaged(true);
        roleContainers[RoleContainerEnum.ViewRoleContainer].setVisible(true);
    }

    private void clearAddRoleFields() {
        ar_usernameField.clear();
        ar_passwordField.clear();
        ar_emailField.clear();
        ar_roleNameComboBox.setValue(null);
    }

    @FXML
    private void submitAddRole(ActionEvent event) {
        try {
            String username = ar_usernameField.getText();
            String password = ar_passwordField.getText();
            String email = ar_emailField.getText();
            String roleIdStr = ar_roleNameComboBox.getValue();
            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || roleIdStr == null) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "All fields are required");
                return;
            }
            Long roleId = Policy.GetRoleId(roleIdStr);

            // Create a new User object and set all fields, including today's date
            user.SetAllFields(email, password, username, roleId);

            Task<Void> insertUserTask = User_dao.insertUserTask(user);

            insertUserTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearAddRoleFields();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully with ID: " + user.getUserId());
                });
            });

            insertUserTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearAddRoleFields();
                    AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Failed to add user: " +
                            insertUserTask.getException().getMessage());
                });
            });

            Worker.submitTask(insertUserTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    private void submitRemoveRole(ActionEvent event) {
        try {
            String id = rr_userEmailIdField.getText();
            if (id.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter an email to remove");
                return;
            }
            Long userId = Long.parseLong(id);

            Task<Void> deleteTask = User_dao.deleteUserTask(userId);
            deleteTask.setOnSucceeded(e2 -> {
                Platform.runLater(() -> {
                    rr_userEmailIdField.clear();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "User removed successfully");
                });
            });

            deleteTask.setOnFailed(e2 -> {
                Platform.runLater(() -> {
                    rr_userEmailIdField.clear();
                    AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Failed to remove user: " +
                            deleteTask.getException().getMessage());
                });
            });

            Worker.submitTask(deleteTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
        }
    }

    private void clearUpdateRoleFields() {
        ur_usernameField.clear();
        ur_passwordField.clear();
        ur_emailField.clear();
        ur_roleNameComboBox.setValue(null);
    }

    private void SetMUFields(User user){
        ur_usernameField.setText(user.getUsername());
        ur_passwordField.setText(user.getPassword());
        ur_emailField.setText(user.getEmail());
    }

    private void SetEditableMUFields(boolean status)
    {
        ur_usernameField.setEditable(status);
        ur_usernameField.setDisable(!status);

        ur_passwordField.setEditable(status);
        ur_passwordField.setDisable(!status);

        ur_emailField.setEditable(status);
        ur_emailField.setDisable(!status);

        ur_roleNameComboBox.setEditable(status);
        ur_roleNameComboBox.setDisable(!status);
    }

    @FXML
    private void verifyUpdateUser(ActionEvent event){
        try {
            String userIdStr = ru_swapField.getText();
            if (userIdStr.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter a User ID to search");
                return;
            }
            Long userId = Long.parseLong(userIdStr);

            Task<User> getUserTask = User_dao.getUserTask(userId);
            getUserTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    SetMUFields(getUserTask.getValue());
                    SetEditableMUFields(true);
                });
            });

            getUserTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    vr_employeeIdSearchField.clear();
                    ErrorHandler.ManageException(getUserTask.getException());
                });
            });

            Worker.submitTask(getUserTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    private void submitUpdateRole(ActionEvent event) {
        try {
            if (currentUserId == null) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please search for a user first in View tab");
                return;
            }

            String username = ur_usernameField.getText();
            String password = ur_passwordField.getText();
            String email = ur_emailField.getText();
            String roleIdStr = ur_roleNameComboBox.getValue();
            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || roleIdStr == null) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "All fields are required");
                return;
            }
            Long roleId = Policy.GetRoleId(roleIdStr);

            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis()); // Today's date
            user.SetAllFields(email, password, username, roleId);
            user.setUserId(currentUserId);
            Task<Void> updateUserTask = User_dao.updateUserTask(user);

            updateUserTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearUpdateRoleFields();
                    currentUserId = null;
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully");
                });
            });

            updateUserTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearUpdateRoleFields();
                    ErrorHandler.ManageException(updateUserTask.getException());
                });
            });

            Worker.submitTask(updateUserTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    private void searchViewRole(ActionEvent event) {
        try {
            String userIdStr = vr_employeeIdSearchField.getText();
            if (userIdStr.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter a User ID to search");
                return;
            }
            Long userId = Long.parseLong(userIdStr);

            Task<User> getUserTask = User_dao.getUserTask(userId);
            getUserTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    vr_UserList.clear();
                    User foundUser = getUserTask.getValue();
                    vr_UserList.add(foundUser);
                    // Populate update fields
                    currentUserId = foundUser.getUserId();
                    ur_usernameField.setText(foundUser.getUsername());
                    ur_passwordField.setText(foundUser.getPassword());
                    ur_emailField.setText(foundUser.getEmail());
                    ur_roleNameComboBox.setValue(foundUser.getRoleId().toString());
                });
            });

            getUserTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    vr_employeeIdSearchField.clear();
                    ErrorHandler.ManageException(getUserTask.getException());
                });
            });

            Worker.submitTask(getUserTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    private void showAllRoles(ActionEvent event) {
        try {
            Task<List<User>> getAllUsersTask = User_dao.getAllUsersTask();
            getAllUsersTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    vr_UserList.clear();
                    vr_UserList.addAll(getAllUsersTask.getValue());
                });
            });

            getAllUsersTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllUsersTask.getException());
                });
            });

            Worker.submitTask(getAllUsersTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
}