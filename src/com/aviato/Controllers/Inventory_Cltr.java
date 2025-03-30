package com.aviato.Controllers;

import com.aviato.Types.Item; // Assuming an Item entity exists
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;
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

    // Inventory Navbar
    @FXML
    private Button addItemBtn;
    @FXML
    private Button removeItemBtn;
    @FXML
    private Button modifyItemBtn;
    @FXML
    private Button viewInvBtn;

    // Add Item Fields
    @FXML
    private TextField ai_itemNameField;
    @FXML
    private TextField ai_quantifyField;
    @FXML
    private TextField ai_PPUField;
    @FXML
    private Text ai_quantityWarning;

    // Remove Item Fields
    @FXML
    private TextField ri_emailField; // Assuming this is repurposed for Item ID
    @FXML
    private Button ri_swapFieldButton;
    @FXML
    private TextField ri_ItemSearchField;
    @FXML
    private Button ri_searchButton;
    @FXML
    private TableView<Item> rc_InventoryTable;
    @FXML
    private TableColumn<Item, String> ItemNameColumn;
    @FXML
    private TableColumn<Item, Integer> ItemQuantityColumn;
    @FXML
    private TableColumn<Item, Double> ItemPPUColumn;

    // Modify Item Fields
    @FXML
    private TextField mi_ItemIdField;
    @FXML
    private Button mi_verifyButton;
    @FXML
    private TextField mi_ItemNameField;
    @FXML
    private TextField mi_QuantityField;
    @FXML
    private TextField mi_PPUField;
    @FXML
    private Button mi_editItemNameButton;
    @FXML
    private Button mi_editQuantityButton;
    @FXML
    private Button mi_editPPUButton;

    // View Item Fields
    @FXML
    private TextField vi_nameSearchField;
    @FXML
    private Button vi_clearButton;
    @FXML
    private Button vi_searchButton;
    @FXML
    private Button vi_allCustomersButton;
    @FXML
    private TableView<Item> vi_InventoryTable;
    @FXML
    private TableColumn<Item, Integer> vi_ItemId;
    @FXML
    private TableColumn<Item, String> vi_ItemName;
    @FXML
    private TableColumn<Item, Integer> vc_QuantityColumn;
    @FXML
    private TableColumn<Item, Double> vi_PPUColumn;

    // Initialize method to set up table columns and containers
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

        // Set up View Item table columns
        vi_ItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        vi_ItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        vc_QuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        vi_PPUColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
    }

    // Inventory NavBar
    private void TurnOffVisibleAndManageInvContainer() {
        for (byte i = 0; i < invContainers.length; i++) {
            invContainers[i].setVisible(false);
            invContainers[i].setManaged(false);
        }
    }

    @FXML
    private void handleInvNavAddItem(ActionEvent event) {
        TurnOffVisibleAndManageInvContainer();
        invContainers[InvContainerEnum.AddItemContainer].setManaged(true);
        invContainers[InvContainerEnum.AddItemContainer].setVisible(true);
    }

    @FXML
    private void handleInvNavRemoveItem(ActionEvent event) {
        TurnOffVisibleAndManageInvContainer();
        invContainers[InvContainerEnum.RemoveItemContainer].setManaged(true);
        invContainers[InvContainerEnum.RemoveItemContainer].setVisible(true);
    }

    @FXML
    private void handleInvNavModifyItem(ActionEvent event) {
        TurnOffVisibleAndManageInvContainer();
        invContainers[InvContainerEnum.ModifyItemContainer].setManaged(true);
        invContainers[InvContainerEnum.ModifyItemContainer].setVisible(true);
    }

    @FXML
    private void handleInvNavViewItem(ActionEvent event) {
        TurnOffVisibleAndManageInvContainer();
        invContainers[InvContainerEnum.ViewItemContainer].setManaged(true);
        invContainers[InvContainerEnum.ViewItemContainer].setVisible(true);
    }

    // Add Item Submit Handler
    @FXML
    private void submitAddItem(ActionEvent event) {
        String itemName = ai_itemNameField.getText();
        String quantityStr = ai_quantifyField.getText();
        String ppuStr = ai_PPUField.getText();

        // Clear fields
        ai_itemNameField.clear();
        ai_quantifyField.clear();
        ai_PPUField.clear();
    }

    // Remove Item Event Handlers
    @FXML
    private void submitRemoveItem(ActionEvent event) {
        // Logic to remove item by ID or other identifier
    }

    @FXML
    private void swapRemoveField(ActionEvent event) {
        System.out.println("Swap field button clicked in Remove Item");
    }

    @FXML
    private void searchRemoveItem(ActionEvent event) {
        String searchTerm = ri_ItemSearchField.getText();
    }

    // Modify Item Event Handlers
    @FXML
    private void verifyItemId(ActionEvent event) {
        // Logic to verify item ID and populate fields
    }

    @FXML
    private void editItemName(ActionEvent event) {
        mi_ItemNameField.setEditable(true);
    }

    @FXML
    private void editQuantity(ActionEvent event) {
        mi_QuantityField.setEditable(true);
    }

    @FXML
    private void editPPU(ActionEvent event) {
        mi_PPUField.setEditable(true);
    }

    @FXML
    private void submitModifyItem(ActionEvent event) {
        // Logic to submit modified item details
    }

    // View Item Event Handlers
    @FXML
    private void clearViewSearch(ActionEvent event) {
        vi_nameSearchField.clear();
        vi_InventoryTable.getItems().clear();
    }

    @FXML
    private void searchViewItem(ActionEvent event) {
        String searchTerm = vi_nameSearchField.getText();
    }

    @FXML
    private void showAllItems(ActionEvent event) {
        // Logic to fetch and display all items
    }

    // Numeric Input Validation
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