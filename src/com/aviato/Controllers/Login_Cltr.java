package com.aviato.Controllers;

import com.aviato.Main;
import com.aviato.Types.Pages;
import com.aviato.Types.User;
import com.aviato.Utils.AlertBox;
import com.aviato.Utils.ErrorHandler;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.db.dao.User_dao;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class Login_Cltr {

    // Inject the TextFields and Button from the FXML
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    public static SideNavBar_Cltr sideBarIns;

    @FXML
    public void initialize() {
        // Any initialization logic can go here (e.g., setting default styles or listeners)
    }

    // Method to handle the login button action
    @FXML
    private void handleLogin(ActionEvent event) {
        try {
            String email = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // Basic validation: check if fields are empty
            if (email.isEmpty() || password.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please fill in both email and password.");
                return;
            }

            // Create a task to authenticate the user via the stored procedure
            Task<String> authenticateTask = User_dao.authenticateUserTask(email, password);

            authenticateTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    String roleName = authenticateTask.getValue();
                    System.out.println(roleName);
                    if (roleName != null && !roleName.isEmpty()) {
                        // Successful login
                        clearFields();
                        Main.SetRoleName(roleName);
                        for(int i =0; i<Main.AllsideNavIns.size(); i++){
                            Main.AllsideNavIns.get(i).AdaptToPolicy();
                        }
                        Main.currentStage.setScene(Pages.GetMainMenuScene(roleName));
                    } else {
                        // This shouldn't happen with the procedure, but as a fallback
                        AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Authentication failed.");
                    }
                });
            });

            authenticateTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearFields();
                    ErrorHandler.ManageException(authenticateTask.getException());
                });
            });

            Worker.submitTask(authenticateTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    // Utility method to clear input fields
    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }
}