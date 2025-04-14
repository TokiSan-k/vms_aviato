package com.aviato.Controllers;

import com.aviato.Main;
import com.aviato.Types.Appointment;
import com.aviato.Types.Invoice;
import com.aviato.Types.InvoiceInfo;
import com.aviato.Types.Pages;
import com.aviato.Utils.AlertBox;
import com.aviato.Utils.ErrorHandler;
import com.aviato.Utils.Field;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.db.dao.Appointment_dao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

public class Appointment_Cltr {
    // Containers
    @FXML
    private VBox mainContainer;

    private VBox[] appointmentContainers = new VBox[5];
    private class AppContainerEnum {
        public static String AppointmentContainerTag = "#appointmentContainer_";
        public static final byte AddAppointmentContainer = 0;
        public static final byte RemoveAppointmentContainer = 1;
        public static final byte ModifyAppointmentContainer = 2;
        public static final byte ViewAppointmentContainer = 3;
        public static final byte ManageInvoiceContainer = 4;
    }

    private final Field[] va_AppointmentSwapFields = { new Field(0,"Cust ID:","Enter Customer ID"),
            new Field(1,"Vehicle ID:","Enter Vehicle ID")};

    private final ObservableList<Appointment> ra_AppList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> va_AppList = FXCollections.observableArrayList();

    private final ObservableList<Invoice> gen_InvoiceList = FXCollections.observableArrayList();

    // Invoice Table Fields (add these)
    @FXML private TableView<Invoice> gen_appointmentTable;
    @FXML private TableColumn<Invoice, Long> gen_InvoiceIdColumn;
    @FXML private TableColumn<Invoice, Long> gen_ServiceIdColumn;
    @FXML private TableColumn<Invoice, Long> gen_AppIdColumn;
    @FXML private TableColumn<Invoice, Date> gen_InvoiceDateColumn;
    @FXML private TableColumn<Invoice, Double> gen_TotalAmountColumn;

    // Appointment Navbar
    @FXML private Button addAppBtn;
    @FXML private Button removeAppBtn;
    @FXML private Button modifyAppBtn;
    @FXML private Button viewAppBtn;

    // Add Appointment Fields
    @FXML private TextField aa_custIdField;
    @FXML private TextField aa_vehicleIdField;
    @FXML private TextField aa_appDateField;
    @FXML private TextField aa_appTimeField;
    @FXML private TextField aa_statusField;
    @FXML private TextField aa_serviceIdField;
    @FXML private TextField aa_empIdField;
    @FXML private Button aa_submitButton;

    // Remove Appointment Fields
    @FXML private TextField ra_appIdField;
    @FXML private Button ra_swapFieldButton;
    @FXML private Button ra_submitButton;
    @FXML private Text ra_searchLabel;
    @FXML private TextField ra_appointmentSearchField;
    @FXML private Button ra_searchButton;
    @FXML private TableView<Appointment> ra_appointmentTable;
    @FXML private TableColumn<Appointment, Long> appointmentIdColumn;
    @FXML private TableColumn<Appointment, Date> appointmentDateColumn;
    @FXML private TableColumn<Appointment, String> appointmentStatusColumn;

    // Modify Appointment Fields
    @FXML private TextField ma_appIdField;
    @FXML private Button ma_verifyButton;
    @FXML private TextField ma_custIdField;
    @FXML private TextField ma_vehicleIdField;
    @FXML private TextField ma_appDateField;
    @FXML private TextField ma_appTimeField;
    @FXML private TextField ma_statusField;
    @FXML private TextField ma_serviceIdField;
    @FXML private TextField ma_empIdField;
    @FXML private Button ma_editCustIdButton;
    @FXML private Button ma_editVehicleIdButton;
    @FXML private Button ma_editAppDateButton;
    @FXML private Button ma_editAppTimeButton;
    @FXML private Button ma_editStatusButton;
    @FXML private Button ma_editServiceIdButton;
    @FXML private Button ma_editEmpIdButton;
    @FXML private Button ma_submitButton;

    // View Appointment Fields
    @FXML private TextField va_SwapSearchField;
    @FXML private Text va_swapSearchLabel;
    @FXML private Button va_clearButton;
    @FXML private Button va_searchButton;
    @FXML private Button va_allAppointmentsButton;
    @FXML private TableView<Appointment> va_appointmentTable;
    @FXML private TableColumn<Appointment, Long> va_appIdColumn;
    @FXML private TableColumn<Appointment, Long> va_custIdColumn;
    @FXML private TableColumn<Appointment, Long> va_vehicleIdColumn;
    @FXML private TableColumn<Appointment, Date> va_appDateColumn;
    @FXML private TableColumn<Appointment, Timestamp> va_appTimeColumn;
    @FXML private TableColumn<Appointment, String> va_statusColumn;
    @FXML private TableColumn<Appointment, Long> va_serviceIdColumn;
    @FXML private TableColumn<Appointment, Long> va_empIdColumn;

    //Generate Invoice
    @FXML private TextField gen_appIdField;
    @FXML private TextField gen_descIdField;
    @FXML private TextField gen_totalAmount;
    @FXML private Button gen_submitBtn;


    private Appointment appointment = new Appointment();
    private boolean isMAVerified = false;

    @FXML
    private SideNavBar_Cltr Appointment_pageCltrController;

    @FXML
    public void initialize() {
        Appointment_pageCltrController.ApplyHighlight("Appointment_page");

        for (byte i = 0; i < appointmentContainers.length; i++) {
            String container = AppContainerEnum.AppointmentContainerTag + i;
            appointmentContainers[i] = (VBox) mainContainer.lookup(container);
        }
        appointmentContainers[AppContainerEnum.AddAppointmentContainer].setVisible(true);
        appointmentContainers[AppContainerEnum.AddAppointmentContainer].setManaged(true);

        // Set up Remove Appointment table columns
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        appointmentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        ra_appointmentTable.setItems(ra_AppList);

        // Set up View Appointment table columns
        va_appIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        va_custIdColumn.setCellValueFactory(new PropertyValueFactory<>("custId"));
        va_vehicleIdColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));
        va_appDateColumn.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        va_appTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appTime"));
        va_statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        va_serviceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        va_empIdColumn.setCellValueFactory(new PropertyValueFactory<>("empId"));
        va_appointmentTable.setItems(va_AppList);

        gen_InvoiceIdColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        gen_ServiceIdColumn.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        gen_AppIdColumn.setCellValueFactory(new PropertyValueFactory<>("appId"));
        gen_InvoiceDateColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
        gen_TotalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        gen_appointmentTable.setItems(gen_InvoiceList);
    }

    @FXML
    private void handleMainMenu()
    {
        Main.currentStage.setScene(Pages.GetMainMenuScene(Main.GetRoleName()));
        Main.currentStage.setTitle("Main Menu");
    }

    // Appointment NavBar
    private void resetUiComps() {
        turnOffVisibleAndManageAppContainer();
        isMAVerified = false;
    }

    private void turnOffVisibleAndManageAppContainer() {
        for (byte i = 0; i < appointmentContainers.length; i++) {
            appointmentContainers[i].setVisible(false);
            appointmentContainers[i].setManaged(false);
        }
    }

    @FXML private void handleAppointmentNavAddApp(ActionEvent event) {
        resetUiComps();
        appointmentContainers[AppContainerEnum.AddAppointmentContainer].setManaged(true);
        appointmentContainers[AppContainerEnum.AddAppointmentContainer].setVisible(true);
    }

    @FXML private void handleAppointmentNavRemoveApp(ActionEvent event) {
        resetUiComps();
        appointmentContainers[AppContainerEnum.RemoveAppointmentContainer].setManaged(true);
        appointmentContainers[AppContainerEnum.RemoveAppointmentContainer].setVisible(true);
    }

    @FXML private void handleAppointmentNavModifyApp(ActionEvent event) {
        resetUiComps();
        appointmentContainers[AppContainerEnum.ModifyAppointmentContainer].setManaged(true);
        appointmentContainers[AppContainerEnum.ModifyAppointmentContainer].setVisible(true);
    }

    @FXML private void handleAppointmentNavViewApp(ActionEvent event) {
        resetUiComps();
        appointmentContainers[AppContainerEnum.ViewAppointmentContainer].setManaged(true);
        appointmentContainers[AppContainerEnum.ViewAppointmentContainer].setVisible(true);
    }

    @FXML private void handleManageInvoicetNavViewApp(ActionEvent event) {
        resetUiComps();
        appointmentContainers[AppContainerEnum.ManageInvoiceContainer].setManaged(true);
        appointmentContainers[AppContainerEnum.ManageInvoiceContainer].setVisible(true);
    }

    private void clearAddAppFields() {
        aa_custIdField.clear();
        aa_vehicleIdField.clear();
        aa_appDateField.clear();
        aa_appTimeField.clear();
        aa_statusField.clear();
        aa_serviceIdField.clear();
        aa_empIdField.clear();
    }

    @FXML
    private void submitAddAppointment(ActionEvent event) {
        try {
            Long custId = Long.parseLong(aa_custIdField.getText());
            Long vehicleId = Long.parseLong(aa_vehicleIdField.getText());
            Date appDate = Date.valueOf(aa_appDateField.getText());
            Timestamp appTime = Timestamp.valueOf(aa_appTimeField.getText());
            String status = aa_statusField.getText();
            Long serviceId = Long.parseLong(aa_serviceIdField.getText());
            Long empId = Long.parseLong(aa_empIdField.getText());

            appointment = new Appointment(custId, vehicleId, appDate, appTime, status, serviceId, empId);
            Task<Void> scheduleTask = Appointment_dao.scheduleAppointmentTask(appointment);

            scheduleTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearAddAppFields();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Appointment scheduled successfully");
                });
            });

            scheduleTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearAddAppFields();
                    ErrorHandler.ManageException(scheduleTask.getException());
                });
            });

            Worker.submitTask(scheduleTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    private void submitRemoveAppointment(ActionEvent event) {
        try {
            String appIdText = ra_appIdField.getText();
            if (appIdText.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter an Appointment ID to remove");
                return;
            }
            Long appId = Long.parseLong(appIdText);

            Task<Void> deleteTask = Appointment_dao.deleteAppointmentTask(appId);
            deleteTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ra_appIdField.clear();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Appointment removed successfully");
                });
            });

            deleteTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ra_appIdField.clear();
                    ErrorHandler.ManageException(deleteTask.getException());
                });
            });

            Worker.submitTask(deleteTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    private int va_swapIdx = 0;
    private int ra_swapIdx = 0;
    @FXML
    private void swapRemoveField(ActionEvent event) {
        ra_swapIdx+=1;
        if(ra_swapIdx > 1)
            ra_swapIdx = 0;

        ra_searchLabel.setText(va_AppointmentSwapFields[ra_swapIdx].Text);
        ra_appointmentSearchField.setPromptText(va_AppointmentSwapFields[ra_swapIdx].Prompt);
    }

    @FXML
    private void searchRemoveAppointment(ActionEvent event) {
        String searchTerm = ra_appointmentSearchField.getText();
        try{
            Long Id = Long.parseLong(searchTerm);
            if(ra_swapIdx == 0){
                searchRAByCustID(Id);
            }else if(ra_swapIdx ==1){
                searchRAByVehicleID(Id);
            }
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Information", ex.getMessage());
        }
    }

    private void searchRAByCustID(Long searchTerm) throws Exception {
        try {
            Task<List<Appointment>> getAllTask = Appointment_dao.searchAllCustIdAppointmentsTask(searchTerm);
            getAllTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ra_AppList.clear();
                    ra_AppList.addAll(getAllTask.getValue());
                });
            });

            getAllTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllTask.getException());
                });
            });

            Worker.submitTask(getAllTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void searchRAByVehicleID(Long searchTerm) throws Exception {
        try {
            Task<List<Appointment>> getAllTask = Appointment_dao.searchAllVehcleIdAppointmentsTask(searchTerm);
            getAllTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ra_AppList.clear();
                    ra_AppList.addAll(getAllTask.getValue());
                });
            });

            getAllTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllTask.getException());
                });
            });

            Worker.submitTask(getAllTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void searchAllRemoveAppointment(ActionEvent event) {
        try {
            Task<List<Appointment>> getAllTask = Appointment_dao.getAllAppointmentsTask();
            getAllTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ra_AppList.clear();
                    ra_AppList.addAll(getAllTask.getValue());
                });
            });

            getAllTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllTask.getException());
                });
            });

            Worker.submitTask(getAllTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void onMAAppointmentVerified(Appointment app) {
        appointment.setAppId(app.getAppId());
        ma_custIdField.setText(String.valueOf(app.getCustId()));
        ma_vehicleIdField.setText(String.valueOf(app.getVehicleId()));
        ma_appDateField.setText(app.getAppDate().toString());
        ma_appTimeField.setText(app.getAppTime().toString());
        ma_statusField.setText(app.getStatus());
        ma_serviceIdField.setText(String.valueOf(app.getServiceId()));
        ma_empIdField.setText(String.valueOf(app.getEmpId()));
    }

    @FXML
    private void verifyAppointmentId(ActionEvent event) {
        try {
            String appIdText = ma_appIdField.getText();
            if (appIdText.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter an Appointment ID to verify");
                return;
            }
            Long appId = Long.parseLong(appIdText);

            Task<Appointment> getAppTask = Appointment_dao.getAppointmentTask(appId);
            getAppTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    onMAAppointmentVerified(getAppTask.getValue());
                    isMAVerified = true;
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Appointment ID Verified!");
                });
            });

            getAppTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAppTask.getException());
                });
            });

            Worker.submitTask(getAppTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    @FXML private void editCustId(ActionEvent event) {
        if (isMAVerified) {
            ma_custIdField.setEditable(true);
            ma_custIdField.setDisable(false);
        }
    }

    @FXML private void editVehicleId(ActionEvent event) {
        if (isMAVerified) {
            ma_vehicleIdField.setEditable(true);
            ma_vehicleIdField.setDisable(false);
        }
    }

    @FXML private void editAppDate(ActionEvent event) {
        if (isMAVerified) {
            ma_appDateField.setEditable(true);
            ma_appDateField.setDisable(false);
        }
    }

    @FXML private void editAppTime(ActionEvent event) {
        if (isMAVerified) {
            ma_appTimeField.setEditable(true);
            ma_appTimeField.setDisable(false);
        }
    }

    @FXML private void editStatus(ActionEvent event) {
        if (isMAVerified) {
            ma_statusField.setEditable(true);
            ma_statusField.setDisable(false);
        }
    }

    @FXML private void editServiceId(ActionEvent event) {
        if (isMAVerified) {
            ma_serviceIdField.setEditable(true);
            ma_serviceIdField.setDisable(false);
        }
    }

    @FXML private void editEmpId(ActionEvent event) {
        if (isMAVerified) {
            ma_empIdField.setEditable(true);
            ma_empIdField.setDisable(false);
        }
    }

    private void clearAllMAFields() {
        ma_custIdField.clear();
        ma_vehicleIdField.clear();
        ma_appDateField.clear();
        ma_appTimeField.clear();
        ma_statusField.clear();
        ma_serviceIdField.clear();
        ma_empIdField.clear();
    }

    private void setEditableMAFields(boolean status) {
        ma_custIdField.setEditable(status);
        ma_custIdField.setDisable(!status);
        ma_vehicleIdField.setEditable(status);
        ma_vehicleIdField.setDisable(!status);
        ma_appDateField.setEditable(status);
        ma_appDateField.setDisable(!status);
        ma_appTimeField.setEditable(status);
        ma_appTimeField.setDisable(!status);
        ma_statusField.setEditable(status);
        ma_statusField.setDisable(!status);
        ma_serviceIdField.setEditable(status);
        ma_serviceIdField.setDisable(!status);
        ma_empIdField.setEditable(status);
        ma_empIdField.setDisable(!status);
    }

    @FXML
    private void submitModifyAppointment(ActionEvent event) {
        try {
            Long appId = Long.parseLong(ma_appIdField.getText());
            String status = ma_statusField.getText();

            Task<Void> updateTask = Appointment_dao.updateAppointmentStatusTask(appId, status);
            updateTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearAllMAFields();
                    setEditableMAFields(false);
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Appointment status updated successfully");
                });
            });

            updateTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearAllMAFields();
                    setEditableMAFields(false);
                    ErrorHandler.ManageException(updateTask.getException());
                });
            });

            Worker.submitTask(updateTask);
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    @FXML
    private void SwapViewSearch(ActionEvent event) {
        va_swapIdx+=1;
        if(va_swapIdx > 1)
            va_swapIdx = 0;

        va_swapSearchLabel.setText(va_AppointmentSwapFields[va_swapIdx].Text);
        va_SwapSearchField.setPromptText(va_AppointmentSwapFields[va_swapIdx].Prompt);
    }

    @FXML
    private void searchViewAppointment(ActionEvent event) {
        String searchTerm = va_SwapSearchField.getText();
        try{
            Long Id = Long.parseLong(searchTerm);
            if(va_swapIdx == 0){
                searchVAByCustID(Id);
            }else if(va_swapIdx ==1){
                searchVAByVehicleID(Id);
            }
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Information", ex.getMessage());
        }
    }


    private void searchVAByCustID(Long searchTerm) throws Exception {
        try {
            Task<List<Appointment>> getAllTask = Appointment_dao.searchAllCustIdAppointmentsTask(searchTerm);
            getAllTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    va_AppList.clear();
                    va_AppList.addAll(getAllTask.getValue());
                });
            });

            getAllTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllTask.getException());
                });
            });

            Worker.submitTask(getAllTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void searchVAByVehicleID(Long searchTerm) throws Exception {
        try {
            Task<List<Appointment>> getAllTask = Appointment_dao.searchAllVehcleIdAppointmentsTask(searchTerm);
            getAllTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    va_AppList.clear();
                    va_AppList.addAll(getAllTask.getValue());
                });
            });

            getAllTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllTask.getException());
                });
            });

            Worker.submitTask(getAllTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }


    @FXML
    private void showAllAppointments(ActionEvent event) {
        try {
            Task<List<Appointment>> getAllTask = Appointment_dao.getAllAppointmentsTask();
            getAllTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    va_AppList.clear();
                    va_AppList.addAll(getAllTask.getValue());
                });
            });

            getAllTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllTask.getException());
                });
            });

            Worker.submitTask(getAllTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void handleGenerateInvoice(ActionEvent event) {
        try {
            Long appId = Long.parseLong(gen_appIdField.getText());
            String description = gen_descIdField.getText();
            Double totalAmount = Double.parseDouble(gen_totalAmount.getText().trim());

            Task<InvoiceInfo> generateTask = Appointment_dao.generateInvoiceTask(appId, description, totalAmount);

            generateTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    InvoiceInfo info = generateTask.getValue();

                    // Confirm success
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Invoice Generated",
                            "Invoice ID: " + info.getInvoiceId() + "\nCustomer: " + info.getCustName());

                    // Generate PDF
                    generateStyledInvoice(info);
                });
            });

            generateTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(generateTask.getException());
                });
            });

            Worker.submitTask(generateTask);

        } catch (Exception ex) {
            ex.printStackTrace();
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    private void generateStyledInvoice(InvoiceInfo info) {
        try {
            String directory = "invoices/";
            File invoiceDir = new File(directory);
            if (!invoiceDir.exists()) {
                invoiceDir.mkdirs();
            }
            String[] name = info.getCustName().split(" ");
            String fName = String.join("", name);
            String fileName = directory + fName + info.getInvoiceId() + ".pdf";
            PdfWriter writer = new PdfWriter(fileName);
            System.out.println("Invoice saved at: " + new File(fileName).getAbsolutePath());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Define custom colors
            DeviceRgb primaryColor = new DeviceRgb(33, 150, 243); // Modern blue
            DeviceRgb secondaryColor = new DeviceRgb(66, 66, 66); // Dark gray
            DeviceRgb accentColor = new DeviceRgb(240, 240, 240); // Light gray for backgrounds

            // Use a professional font (fallback to Helvetica if custom font unavailable)
            PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont regularFont = PdfFontFactory.createFont("Helvetica");

            // Header Section
            Table headerTable = new Table(UnitValue.createPercentArray(new float[]{20, 80})).useAllAvailableWidth();

            // Company Details
            Div companyDetails = new Div()
                    .add(new Paragraph("VMS | Aviato™")
                            .setFont(boldFont)
                            .setFontSize(18)
                            .setFontColor(primaryColor))
                    .add(new Paragraph("1234 Auto Lane, Tech City, TX 75001")
                            .setFont(regularFont)
                            .setFontSize(10))
                    .add(new Paragraph("Email: support@aviato.com | Phone: (123) 456-7890")
                            .setFont(regularFont)
                            .setFontSize(10));
            headerTable.addCell(new com.itextpdf.layout.element.Cell().add(companyDetails)
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT));
            document.add(headerTable);

            // Divider Line
            SolidLine line = new SolidLine(1f);
            line.setColor(primaryColor);
            LineSeparator separator = new LineSeparator(line);
            separator.setMarginTop(10);
            separator.setMarginBottom(20);
            document.add(separator);

            // Title
            Paragraph title = new Paragraph("INVOICE")
                    .setFont(boldFont)
                    .setFontSize(24)
                    .setFontColor(secondaryColor)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Invoice Details (in a two-column layout)
            Table infoTable = new Table(UnitValue.createPercentArray(new float[]{50, 50})).useAllAvailableWidth();
            Div invoiceInfo = new Div()
                    .add(new Paragraph("Invoice #: " + info.getInvoiceId()).setFont(regularFont).setFontSize(11))
                    .add(new Paragraph("Date: " + info.getInvoiceDate()).setFont(regularFont).setFontSize(11))
                    .add(new Paragraph("Vehicle: " + info.getLicencePlate()).setFont(regularFont).setFontSize(11));
            Div billedTo = new Div()
                    .add(new Paragraph("Billed To:").setFont(boldFont).setFontSize(12))
                    .add(new Paragraph(info.getCustName()).setFont(regularFont).setFontSize(11))
                    .add(new Paragraph(info.getAddress()).setFont(regularFont).setFontSize(11))
                    .add(new Paragraph("Email: " + info.getEmail()).setFont(regularFont).setFontSize(11))
                    .add(new Paragraph("Contact: " + info.getContact()).setFont(regularFont).setFontSize(11));
            infoTable.addCell(new com.itextpdf.layout.element.Cell().add(invoiceInfo)
                    .setBorder(Border.NO_BORDER)
                    .setPadding(10));
            infoTable.addCell(new com.itextpdf.layout.element.Cell().add(billedTo)
                    .setBorder(Border.NO_BORDER)
                    .setPadding(10));
            document.add(infoTable);

            // Service Table
            float[] cols = {70, 30};
            Table serviceTable = new Table(UnitValue.createPercentArray(cols))
                    .useAllAvailableWidth()
                    .setMarginTop(20)
                    .setBackgroundColor(accentColor)
                    .setBorder(new SolidBorder(ColorConstants.LIGHT_GRAY, 1));
            serviceTable.addHeaderCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("Description").setFont(boldFont).setFontSize(11))
                    .setBackgroundColor(primaryColor)
                    .setFontColor(ColorConstants.WHITE)
                    .setPadding(8));
            serviceTable.addHeaderCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("Amount").setFont(boldFont).setFontSize(11))
                    .setBackgroundColor(primaryColor)
                    .setFontColor(ColorConstants.WHITE)
                    .setPadding(8)
                    .setTextAlignment(TextAlignment.RIGHT));
            serviceTable.addCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph(info.getDescription()).setFont(regularFont).setFontSize(11))
                    .setPadding(8));
            serviceTable.addCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("₹" + String.format("%.2f", info.getTotalAmount())).setFont(regularFont).setFontSize(11))
                    .setPadding(8)
                    .setTextAlignment(TextAlignment.RIGHT));
            document.add(serviceTable);

            // Total Amount
            Table totalTable = new Table(UnitValue.createPercentArray(new float[]{70, 30})).useAllAvailableWidth();
            totalTable.addCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("Total").setFont(boldFont).setFontSize(12))
                    .setBorder(Border.NO_BORDER)
                    .setPaddingTop(10)
                    .setTextAlignment(TextAlignment.RIGHT));
            totalTable.addCell(new com.itextpdf.layout.element.Cell()
                    .add(new Paragraph("₹" + String.format("%.2f", info.getTotalAmount())).setFont(boldFont).setFontSize(12))
                    .setBorder(Border.NO_BORDER)
                    .setPaddingTop(10)
                    .setTextAlignment(TextAlignment.RIGHT));
            document.add(totalTable);

            // Footer
            Div footer = new Div()
                    .add(new Paragraph("Thank you for choosing VMS | Aviato™")
                            .setFont(regularFont)
                            .setFontSize(10)
                            .setFontColor(secondaryColor)
                            .setTextAlignment(TextAlignment.CENTER))
                    .add(new Paragraph("Contact us at support@aviato.com or (123) 456-7890")
                            .setFont(regularFont)
                            .setFontSize(10)
                            .setFontColor(secondaryColor)
                            .setTextAlignment(TextAlignment.CENTER))
                    .add(new Paragraph("2025 © VMS | Aviato™. All Rights Reserved.")
                            .setFont(regularFont)
                            .setFontSize(10)
                            .setFontColor(secondaryColor)
                            .setTextAlignment(TextAlignment.CENTER));
            document.add(footer.setMarginTop(40));


            document.close();
            AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Invoice generated and saved as " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "PDF Error", "Error creating PDF: " + e.getMessage());
        }
    }

    @FXML
    private void searchAllInvoices(ActionEvent event){
        try {
            Task<List<Invoice>> getAllInvoicesTask = Appointment_dao.getAllInvoicesTask();
            getAllInvoicesTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    gen_InvoiceList.clear();
                    gen_InvoiceList.addAll(getAllInvoicesTask.getValue());
                });
            });

            getAllInvoicesTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ErrorHandler.ManageException(getAllInvoicesTask.getException());
                });
            });

            Worker.submitTask(getAllInvoicesTask);
        } catch (Exception e) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch invoices: " + e.getMessage());
        }
    }

}




