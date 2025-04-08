package com.aviato.Utils;

import javafx.scene.control.Alert;

import java.sql.SQLException;

public class ErrorHandler
{
    public static void ManageException(Throwable exception)
    {
        String err;
        if (exception != null) {
            Throwable cause = exception;
            SQLException sqlEx = null;
            while (cause != null) {
                if (cause instanceof SQLException) {
                    sqlEx = (SQLException) cause;
                    break;
                }
                cause = cause.getCause();
            }

            if (sqlEx != null) {
                err = sqlEx.getMessage().split("\n")[0];
            } else {
                cause = exception;
                while (cause.getCause() != null) {
                    cause = cause.getCause();
                }
                err = cause.getMessage();
            }
            exception.printStackTrace();
            AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Information", err);
        } else {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Unknown error occurred.");
        }
    }
}
