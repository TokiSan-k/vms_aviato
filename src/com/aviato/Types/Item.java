package com.aviato.Types;

import javax.persistence.*;

@Entity
@Table(name = "inventory")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "InsertItem",
                procedureName = "inventory_pkg.add_inventory_item",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_item_name", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_quantity", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_price_per_unit", type = Double.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_item_id_out", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetItem",
                procedureName = "inventory_pkg.get_inventory",
                resultClasses = Item.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_item_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "UpdateItem",
                procedureName = "inventory_pkg.update_inventory",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_item_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_quantity", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_price_per_unit", type = Double.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "DeleteItem",
                procedureName = "inventory_pkg.delete_inventory",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_item_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_deleted_item_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetAllItems",
                procedureName = "inventory_pkg.get_inventory",
                resultClasses = Item.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_item_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        )
})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "inventory_seq", allocationSize = 1)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "quantity")
    private Integer Quantity;

    @Column(name = "price_per_unit")
    private Double PricePerUnit;

    // Getters and Setters
    public Long getItemId() { return itemId;}
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Integer getQuantity() { return Quantity; }
    public void setQuantity(Integer quantity) { this.Quantity = quantity; }

    public Double getPricePerUnit() { return PricePerUnit; }
    public void setPricePerUnit(Double pricePerUnit) { this.PricePerUnit = pricePerUnit; }

    public Item() {}

    public Item(String itemname, Integer quantity, Double pricePerUnit) {
        this.itemName = itemname;
        this.Quantity = quantity;
        this.PricePerUnit = pricePerUnit;
    }
}