package com.aviato.Utils;

import javafx.scene.control.Alert;

public class AlertBox {

    private static Alert alert = new Alert(Alert.AlertType.ERROR);

    public static void ShowAlert(Alert.AlertType type, String title, String message)
    {
        alert.setAlertType(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}