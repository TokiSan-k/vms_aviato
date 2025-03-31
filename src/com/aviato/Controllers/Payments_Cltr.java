package com.aviato.Controllers;

import com.aviato.Types.Payment;
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
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
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
        public static final byte ViewPaymentsContainer = 1;
        public static final byte UpdateStatusContainer = 2;
    }

    // Payment Navbar
    @FXML
    private Button addPaymentBtn;
    @FXML
    private Button viewPaymentsBtn;
    @FXML
    private Button updateStatusBtn;

    // Add Payment Fields
    @FXML
    private TextField ap_invoiceIdField;
    @FXML
    private TextField ap_amountPaidField;
    @FXML
    private TextField ap_paymentMethodField;
    @FXML
    private TextField ap_statusField;

    // View Payments Fields
    @FXML
    private TextField vp_paymentIdSearchField;
    @FXML
    private Button vp_clearButton;
    @FXML
    private Button vp_searchButton;
    @FXML
    private Button vp_allPaymentsButton;
    @FXML
    private TableView<Payment> vp_paymentsTable;
    @FXML
    private TableColumn<Payment, Long> vp_paymentIdColumn;
    @FXML
    private TableColumn<Payment, Long> vp_invoiceIdColumn;
    @FXML
    private TableColumn<Payment, Date> vp_paymentDateColumn;
    @FXML
    private TableColumn<Payment, BigDecimal> vp_amountPaidColumn;
    @FXML
    private TableColumn<Payment, String> vp_paymentMethodColumn;
    @FXML
    private TableColumn<Payment, String> vp_statusColumn;

    // Update Status Fields
    @FXML
    private TextField us_paymentIdField;
    @FXML
    private Button us_verifyButton;
    @FXML
    private TextField us_newStatusField;

    // Initialize method to set up table columns and JPA
    @FXML
    public void initialize() {
        // Initialize containers
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
    }

    // Payment NavBar
    private void turnOffVisibleAndManagePaymentContainer() {
        for (byte i = 0; i < paymentContainers.length; i++) {
            paymentContainers[i].setVisible(false);
            paymentContainers[i].setManaged(false);
        }
    }

    @FXML
    private void handlePaymentNavAddPayment(ActionEvent event) {
        turnOffVisibleAndManagePaymentContainer();
        paymentContainers[PaymentContainerEnum.AddPaymentContainer].setManaged(true);
        paymentContainers[PaymentContainerEnum.AddPaymentContainer].setVisible(true);
    }

    @FXML
    private void handlePaymentNavViewPayments(ActionEvent event) {
        turnOffVisibleAndManagePaymentContainer();
        paymentContainers[PaymentContainerEnum.ViewPaymentsContainer].setManaged(true);
        paymentContainers[PaymentContainerEnum.ViewPaymentsContainer].setVisible(true);
    }

    @FXML
    private void handlePaymentNavUpdateStatus(ActionEvent event) {
        turnOffVisibleAndManagePaymentContainer();
        paymentContainers[PaymentContainerEnum.UpdateStatusContainer].setManaged(true);
        paymentContainers[PaymentContainerEnum.UpdateStatusContainer].setVisible(true);
    }

    // Add Payment Submit Handler
    @FXML
    private void submitAddPayment(ActionEvent event) {
        try {
            Long invoiceId = Long.parseLong(ap_invoiceIdField.getText());
            BigDecimal amountPaid = new BigDecimal(ap_amountPaidField.getText());
            String paymentMethod = ap_paymentMethodField.getText();
            String status = ap_statusField.getText();

            // Clear fields
            ap_invoiceIdField.clear();
            ap_amountPaidField.clear();
            ap_paymentMethodField.clear();
            ap_statusField.clear();
        } catch (Exception e) {
            System.err.println("Error recording payment: " + e.getMessage());
        }
    }

    // View Payments Event Handlers
    @FXML
    private void clearViewSearch(ActionEvent event) {
        vp_paymentIdSearchField.clear();
        vp_paymentsTable.getItems().clear();
    }

    @FXML
    private void searchViewPayment(ActionEvent event) {
        try {
            Long paymentId = Long.parseLong(vp_paymentIdSearchField.getText());

        } catch (Exception e) {
            System.err.println("Error retrieving payment: " + e.getMessage());

        }
    }

    @FXML
    private void showAllPayments(ActionEvent event) {


    }

    // Update Status Event Handlers
    @FXML
    private void verifyPaymentId(ActionEvent event) {
        try {
            Long paymentId = Long.parseLong(us_paymentIdField.getText());

        } catch (Exception e) {
            System.err.println("Error verifying payment ID: " + e.getMessage());
        }
    }

    @FXML
    private void submitUpdateStatus(ActionEvent event) {
        try {
            Long paymentId = Long.parseLong(us_paymentIdField.getText());
            String newStatus = us_newStatusField.getText();


            // Clear fields
            us_paymentIdField.clear();
            us_newStatusField.clear();
            us_newStatusField.setEditable(false);
        } catch (Exception e) {
            System.err.println("Error updating payment status: " + e.getMessage());
        }
    }
}