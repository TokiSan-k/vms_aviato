package com.aviato.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Login_Controller {

    // Inject the TextFields and Button from the FXML
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    // Method to handle the login button action
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Basic validation: check if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please fill in both username and password.");
            return;
        }

        // Example login logic (replace with your actual authentication logic)
        if (username.equals("admin") && password.equals("password123")) {
            showAlert("Success", "Login successful! Welcome, " + username + "!");
        } else {
            showAlert("Error", "Invalid username or password.");
        }
    }

    // Utility method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}