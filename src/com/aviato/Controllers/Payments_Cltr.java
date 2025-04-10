package com.aviato.Controllers;

import com.aviato.Types.Payment;
import com.aviato.Utils.AlertBox;
import com.aviato.Utils.ErrorHandler;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.db.dao.Payment_dao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Payments_Cltr {
    // Containers
    @FXML
    private VBox mainContainer;

    private VBox[] paymentContainers = new VBox[3];
    private class PaymentContainerEnum {
        public static String PaymentContainerTag = "#paymentContainer_";
        public static final byte AddPaymentContainer = 0;
        public static final byte UpdateStatusContainer = 1;
        public static final byte ViewPaymentsContainer = 2;
    }

    private final ObservableList<Payment> vp_PaymentList = FXCollections.observableArrayList();

    // Payment Navbar
    @FXML private Button addPaymentBtn;
    @FXML private Button viewPaymentsBtn;
    @FXML private Button updateStatusBtn;

    // Add Payment Fields
    @FXML private TextField ap_invoiceIdField;
    @FXML private TextField ap_amountPaidField;
    @FXML private TextField ap_paymentMethodField;
    @FXML private TextField ap_statusField;

    // View Payments Fields
    @FXML private TextField vp_paymentIdSearchField;
    @FXML private Button vp_clearButton;
    @FXML private Button vp_searchButton;
    @FXML private Button vp_allPaymentsButton;
    @FXML private TableView<Payment> vp_paymentsTable;
    @FXML private TableColumn<Payment, Long> vp_paymentIdColumn;
    @FXML private TableColumn<Payment, Long> vp_invoiceIdColumn;
    @FXML private TableColumn<Payment, Date> vp_paymentDateColumn;
    @FXML private TableColumn<Payment, BigDecimal> vp_amountPaidColumn;
    @FXML private TableColumn<Payment, String> vp_paymentMethodColumn;
    @FXML private TableColumn<Payment, String> vp_statusColumn;

    // Update Status Fields
    @FXML private TextField us_paymentIdField;
    @FXML private Button us_verifyButton;
    @FXML private TextField us_newStatusField;

    private Payment payment = new Payment();
    private boolean isUSVerified = false;

    @FXML
    public void initialize() {
        for (byte i = 0; i < paymentContainers.length; i++) {
            String container = PaymentContainerEnum.PaymentContainerTag + i;
            paymentContainers[i] = (VBox) mainContainer.lookup(container);
        }
        paymentContainers[PaymentContainerEnum.AddPaymentContainer].setVisible(true);
        paymentContainers[PaymentContainerEnum.AddPaymentContainer].setManaged(true);

        // Set up View Payments table columns
        vp_paymentIdColumn.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        vp_invoiceIdColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        vp_paymentDateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        vp_amountPaidColumn.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        vp_paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        vp_statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        vp_paymentsTable.setItems(vp_PaymentList);
    }

    // Payment NavBar
    private void resetUiComps() {
        turnOffVisibleAndManagePaymentContainer();
        isUSVerified = false;
    }

    private void turnOffVisibleAndManagePaymentContainer() {
        for (byte i = 0; i < paymentContainers.length; i++) {
            paymentContainers[i].setVisible(false);
            paymentContainers[i].setManaged(false);
        }
    }

    @FXML private void handlePaymentNavAddPayment(ActionEvent event) {
        resetUiComps();
        paymentContainers[PaymentContainerEnum.AddPaymentContainer].setManaged(true);
        paymentContainers[PaymentContainerEnum.AddPaymentContainer].setVisible(true);
    }

    @FXML private void handlePaymentNavViewPayments(ActionEvent event) {
        resetUiComps();
        paymentContainers[PaymentContainerEnum.ViewPaymentsContainer].setManaged(true);
        paymentContainers[PaymentContainerEnum.ViewPaymentsContainer].setVisible(true);
    }

    @FXML private void handlePaymentNavUpdateStatus(ActionEvent event) {
        resetUiComps();
        paymentContainers[PaymentContainerEnum.UpdateStatusContainer].setManaged(true);
        paymentContainers[PaymentContainerEnum.UpdateStatusContainer].setVisible(true);
    }

    private void clearAddPaymentFields() {
        ap_invoiceIdField.clear();
        ap_amountPaidField.clear();
        ap_paymentMethodField.clear();
        ap_statusField.clear();
    }

    @FXML
    private void submitAddPayment(ActionEvent event) {
        try {
            Long invoiceId = Long.parseLong(ap_invoiceIdField.getText());
            BigDecimal amountPaid = new BigDecimal(ap_amountPaidField.getText());
            String paymentMethod = ap_paymentMethodField.getText();
            String status = ap_statusField.getText();

            payment = new Payment(invoiceId, null, amountPaid, paymentMethod, status); // paymentDate set by DB
            Task<Void> recordTask = Payment_dao.recordPaymentTask(payment);

            recordTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearAddPaymentFields();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Payment recorded successfully");
                });
            });

            recordTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearAddPaymentFields();
                    ErrorHandler.ManageException(recordTask.getException());
                });
            });

            Worker.submitTask(recordTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    private void searchViewPayment(ActionEvent event) {
        try {
            String paymentIdText = vp_paymentIdSearchField.getText();
            if (paymentIdText.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter a Payment ID to search");
                return;
            }
            Long paymentId = Long.parseLong(paymentIdText);

            Task<Payment> getPaymentTask = Payment_dao.getPaymentByIdTask(paymentId);
            getPaymentTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    vp_PaymentList.clear();
                    vp_PaymentList.add(getPaymentTask.getValue());
                });
            });

            getPaymentTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getPaymentTask.getException());
                });
            });

            Worker.submitTask(getPaymentTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    private void showAllPayments(ActionEvent event) {
        try {
            Task<List<Payment>> getAllPaymentsTask = Payment_dao.getAllPaymentsTask();

            getAllPaymentsTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    vp_PaymentList.clear();
                    vp_PaymentList.addAll(getAllPaymentsTask.getValue());
                });
            });

            getAllPaymentsTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllPaymentsTask.getException());
                });
            });

            Worker.submitTask(getAllPaymentsTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Failed to retrieve payments: " + ex.getMessage());
        }
    }

    private void onUSPaymentVerified(Payment payment) {
        this.payment.setPaymentId(payment.getPaymentId());
        us_newStatusField.setText(payment.getStatus());
    }

    @FXML
    private void verifyPaymentId(ActionEvent event) {
        try {
            String paymentIdText = us_paymentIdField.getText();
            if (paymentIdText.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter a Payment ID to verify");
                return;
            }
            Long paymentId = Long.parseLong(paymentIdText);

            Task<Payment> getPaymentTask = Payment_dao.getPaymentByIdTask(paymentId);
            getPaymentTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    onUSPaymentVerified(getPaymentTask.getValue());
                    isUSVerified = true;
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Payment ID Verified!");
                });
            });

            getPaymentTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getPaymentTask.getException());
                });
            });

            Worker.submitTask(getPaymentTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    private void clearUpdateStatusFields() {
        us_paymentIdField.clear();
        us_newStatusField.clear();
    }

    private void setEditableUSFields(boolean status) {
        us_newStatusField.setEditable(status);
        us_newStatusField.setDisable(!status);
    }

    @FXML
    private void submitUpdateStatus(ActionEvent event) {
        try {
            Long paymentId = Long.parseLong(us_paymentIdField.getText());
            String newStatus = us_newStatusField.getText();

            Task<Void> updateTask = Payment_dao.updatePaymentStatusTask(paymentId, newStatus);
            updateTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearUpdateStatusFields();
                    setEditableUSFields(false);
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Payment status updated successfully");
                });
            });

            updateTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearUpdateStatusFields();
                    setEditableUSFields(false);
                    ErrorHandler.ManageException(updateTask.getException());
                });
            });

            Worker.submitTask(updateTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }
}