package com.aviato.Controllers;

import com.aviato.Types.Item;
import com.aviato.Utils.AlertBox;
import com.aviato.Utils.ErrorHandler;
import com.aviato.Utils.concurrency.Worker;
import com.aviato.db.dao.Inventory_dao;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.List;

public class Inventory_Cltr {

    // Containers
    @FXML
    private VBox mainContainer;

    private VBox[] invContainers = new VBox[4];
    private class InvContainerEnum {
        public static String InvContainerTag = "#invContainer_";
        public static final byte AddItemContainer = 0;
        public static final byte RemoveItemContainer = 1;
        public static final byte ModifyItemContainer = 2;
        public static final byte ViewItemContainer = 3;
    }

    private final ObservableList<Item> ri_ItemList = FXCollections.observableArrayList();
    private final ObservableList<Item> vi_ItemList = FXCollections.observableArrayList();

    // Inventory Navbar
    @FXML private Button addItemBtn;
    @FXML private Button removeItemBtn;
    @FXML private Button modifyItemBtn;
    @FXML private Button viewInvBtn;

    // Add Item Fields
    @FXML private TextField ai_itemNameField;
    @FXML private TextField ai_quantifyField; // Note: Typo in FXML ("quantify" instead of "quantity")
    @FXML private TextField ai_PPUField;
    @FXML private Text ai_quantityWarning;

    // Remove Item Fields
    @FXML private TextField ri_emailField; // Repurposed as Item ID field
    @FXML private Button ri_swapFieldButton;
    @FXML private TextField ri_ItemSearchField;
    @FXML private Button ri_searchButton;
    @FXML private TableView<Item> rc_InventoryTable; // Note: Typo in variable name (rc_ vs ri_)
    @FXML private TableColumn<Item, String> ItemNameColumn;
    @FXML private TableColumn<Item, Integer> ItemQuantityColumn;
    @FXML private TableColumn<Item, Double> ItemPPUColumn;

    // Modify Item Fields
    @FXML private TextField mi_ItemIdField;
    @FXML private Button mi_verifyButton;
    @FXML private TextField mi_ItemNameField;
    @FXML private TextField mi_QuantityField;
    @FXML private TextField mi_PPUField;
    @FXML private Button mi_editItemNameButton;
    @FXML private Button mi_editQuantityButton;
    @FXML private Button mi_editPPUButton;

    // View Item Fields
    @FXML private TextField vi_nameSearchField;
    @FXML private Button vi_clearButton;
    @FXML private Button vi_searchButton;
    @FXML private Button vi_allCustomersButton; // Note: Misnamed in FXML (should be vi_allItemsButton)
    @FXML private TableView<Item> vi_InventoryTable;
    @FXML private TableColumn<Item, Long> vi_ItemId; // Note: Type mismatch (should be Long)
    @FXML private TableColumn<Item, String> vi_ItemName;
    @FXML private TableColumn<Item, Integer> vc_QuantityColumn;
    @FXML private TableColumn<Item, Double> vi_PPUColumn;

    private Item item = new Item();
    private boolean isMIVerified = false;

    @FXML
    public void initialize() {
        for (byte i = 0; i < invContainers.length; i++) {
            String container = InvContainerEnum.InvContainerTag + i;
            invContainers[i] = (VBox) mainContainer.lookup(container);
        }
        invContainers[InvContainerEnum.AddItemContainer].setVisible(true);
        invContainers[InvContainerEnum.AddItemContainer].setManaged(true);

        // Set up Remove Item table columns
        ItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        ItemQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ItemPPUColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
        rc_InventoryTable.setItems(ri_ItemList);

        // Set up View Item table columns
        vi_ItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        vi_ItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        vc_QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        vi_PPUColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
        vi_InventoryTable.setItems(vi_ItemList);
    }

    // Inventory NavBar
    private void resetUiComps() {
        turnOffVisibleAndManageInvContainer();
        isMIVerified = false;
    }

    private void turnOffVisibleAndManageInvContainer() {
        for (byte i = 0; i < invContainers.length; i++) {
            invContainers[i].setVisible(false);
            invContainers[i].setManaged(false);
        }
    }

    @FXML private void handleInvNavAddItem(ActionEvent event) {
        resetUiComps();
        invContainers[InvContainerEnum.AddItemContainer].setManaged(true);
        invContainers[InvContainerEnum.AddItemContainer].setVisible(true);
    }

    @FXML private void handleInvNavRemoveItem(ActionEvent event) {
        resetUiComps();
        invContainers[InvContainerEnum.RemoveItemContainer].setManaged(true);
        invContainers[InvContainerEnum.RemoveItemContainer].setVisible(true);
    }

    @FXML private void handleInvNavModifyItem(ActionEvent event) {
        resetUiComps();
        invContainers[InvContainerEnum.ModifyItemContainer].setManaged(true);
        invContainers[InvContainerEnum.ModifyItemContainer].setVisible(true);
    }

    @FXML private void handleInvNavViewItem(ActionEvent event) {
        resetUiComps();
        invContainers[InvContainerEnum.ViewItemContainer].setManaged(true);
        invContainers[InvContainerEnum.ViewItemContainer].setVisible(true);
    }

    private void clearAddItemFields() {
        ai_itemNameField.clear();
        ai_quantifyField.clear();
        ai_PPUField.clear();
    }

    @FXML
    private void submitAddItem(ActionEvent event) {
        try {
            String itemName = ai_itemNameField.getText();
            Integer quantity = Integer.parseInt(ai_quantifyField.getText());
            Double pricePerUnit = Double.parseDouble(ai_PPUField.getText());

            item = new Item(itemName, quantity, pricePerUnit);
            Task<Void> insertTask = Inventory_dao.insertItemTask(item);

            insertTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearAddItemFields();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Item added successfully");
                });
            });

            insertTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearAddItemFields();
                    ErrorHandler.ManageException(insertTask.getException());
                });
            });

            Worker.submitTask(insertTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Error", "Invalid input: " + ex.getMessage());
        }
    }

    @FXML
    private void submitRemoveItem(ActionEvent event) {
        try {
            String itemIdText = ri_emailField.getText();
            if (itemIdText.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter an Item ID to remove");
                return;
            }
            Long itemId = Long.parseLong(itemIdText);

            Task<Void> deleteTask = Inventory_dao.deleteItemTask(itemId);
            deleteTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ri_emailField.clear();
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Item removed successfully");
                });
            });

            deleteTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    ri_emailField.clear();
                    ErrorHandler.ManageException(deleteTask.getException());
                });
            });

            Worker.submitTask(deleteTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    @FXML
    private void swapRemoveField(ActionEvent event) {
        // Placeholder for swapping field (e.g., toggle between ID and another field)
        System.out.println("Swap field button clicked in Remove Item");
    }

    @FXML
    private void searchRemoveItem(ActionEvent event) {
        String searchTerm = ri_ItemSearchField.getText();
        // Add search logic here if needed
    }

    @FXML
    private void searchAllRemoveItem(ActionEvent event) {
        try {
            Task<List<Item>> getAllTask = Inventory_dao.getAllItemsTask();
            getAllTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    ri_ItemList.clear();
                    ri_ItemList.addAll(getAllTask.getValue());
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

    private void onMIItemVerified(Item item) {
        this.item.setItemId(item.getItemId());
        mi_ItemNameField.setText(item.getItemName());
        mi_QuantityField.setText(String.valueOf(item.getQuantity()));
        mi_PPUField.setText(String.valueOf(item.getPricePerUnit()));
    }

    @FXML
    private void verifyItemId(ActionEvent event) {
        try {
            String itemIdText = mi_ItemIdField.getText();
            if (itemIdText.isEmpty()) {
                AlertBox.ShowAlert(Alert.AlertType.WARNING, "Warning", "Please enter an Item ID to verify");
                return;
            }
            Long itemId = Long.parseLong(itemIdText);

            Task<Item> getItemTask = Inventory_dao.getItemTask(itemId);
            getItemTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    onMIItemVerified(getItemTask.getValue());
                    isMIVerified = true;
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Item ID Verified!");
                });
            });

            getItemTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                        ErrorHandler.ManageException(getItemTask.getException());
                });
            });

            Worker.submitTask(getItemTask);
        } catch (Exception ex) {
            AlertBox.ShowAlert(Alert.AlertType.ERROR, "Exception", ex.getMessage());
        }
    }

    @FXML private void editItemName(ActionEvent event) {
        if (isMIVerified) {
            mi_ItemNameField.setEditable(true);
            mi_ItemNameField.setDisable(false);
        }
    }

    @FXML private void editQuantity(ActionEvent event) {
        if (isMIVerified) {
            mi_QuantityField.setEditable(true);
            mi_QuantityField.setDisable(false);
        }
    }

    @FXML private void editPPU(ActionEvent event) {
        if (isMIVerified) {
            mi_PPUField.setEditable(true);
            mi_PPUField.setDisable(false);
        }
    }

    private void clearAllMIFields() {
        mi_ItemNameField.clear();
        mi_QuantityField.clear();
        mi_PPUField.clear();
    }

    private void setEditableMIFields(boolean status) {
        mi_ItemNameField.setEditable(status);
        mi_ItemNameField.setDisable(!status);
        mi_QuantityField.setEditable(status);
        mi_QuantityField.setDisable(!status);
        mi_PPUField.setEditable(status);
        mi_PPUField.setDisable(!status);
    }

    @FXML
    private void submitModifyItem(ActionEvent event) {
        try {
            item.setItemId(Long.parseLong(mi_ItemIdField.getText()));
            item.setItemName(mi_ItemNameField.getText());
            item.setQuantity(Integer.parseInt(mi_QuantityField.getText()));
            item.setPricePerUnit(Double.parseDouble(mi_PPUField.getText()));

            Task<Void> updateTask = Inventory_dao.updateItemTask(item);
            updateTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    clearAllMIFields();
                    setEditableMIFields(false);
                    AlertBox.ShowAlert(Alert.AlertType.INFORMATION, "Success", "Item modified successfully");
                });
            });

            updateTask.setOnFailed(e -> {
                Platform.runLater(() -> {
                    clearAllMIFields();
                    setEditableMIFields(false);
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
    private void clearViewSearch(ActionEvent event) {
        vi_nameSearchField.clear();
        vi_InventoryTable.getItems().clear();
    }

    @FXML
    private void searchViewItem(ActionEvent event) {
        String searchTerm = vi_nameSearchField.getText();
        // Add search logic here if needed
    }

    @FXML
    private void showAllItems(ActionEvent event) {
        try {
            Task<List<Item>> getAllTask = Inventory_dao.getAllItemsTask();
            getAllTask.setOnSucceeded(e -> {
                Platform.runLater(() -> {
                    vi_ItemList.clear();
                    vi_ItemList.addAll(getAllTask.getValue());
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
    private void handleNumericInput(KeyEvent event) {
        if (!event.getCharacter().matches("[0-9]")) {
            ai_quantityWarning.setVisible(true);
            ai_quantityWarning.setManaged(true);
            event.consume();
        } else {
            ai_quantityWarning.setVisible(false);
            ai_quantityWarning.setManaged(false);
        }
    }

    @FXML
    private void handleModifyNumericInput(KeyEvent event) {
        if (!event.getCharacter().matches("[0-9]")) {
            ai_quantityWarning.setVisible(true);
            ai_quantityWarning.setManaged(true);
            event.consume();
        } else {
            ai_quantityWarning.setVisible(false);
            ai_quantityWarning.setManaged(false);
        }
    }

    @FXML
    private void handlePPUInput(KeyEvent event) {
        String newText = ai_PPUField.getText() + event.getCharacter();
        if (!newText.matches("\\d*\\.?\\d*") || newText.indexOf('.') != newText.lastIndexOf('.')) {
            ai_quantityWarning.setVisible(true);
            ai_quantityWarning.setManaged(true);
            event.consume();
        } else {
            ai_quantityWarning.setVisible(false);
            ai_quantityWarning.setManaged(false);
        }
    }

    @FXML
    private void handleModifyPPUInput(KeyEvent event) {
        String newText = mi_PPUField.getText() + event.getCharacter();
        if (!newText.matches("\\d*\\.?\\d*") || newText.indexOf('.') != newText.lastIndexOf('.')) {
            ai_quantityWarning.setVisible(true);
            ai_quantityWarning.setManaged(true);
            event.consume();
        } else {
            ai_quantityWarning.setVisible(false);
            ai_quantityWarning.setManaged(false);
        }
    }
}