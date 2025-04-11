package com.aviato.Types;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "service_inventory")
public class ServiceItem implements Serializable{

    @Id
    @Column(name = "service_id")
    private Long serviceId;

    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "quantity_used")
    private Integer quantityUsed;

    // Getters and Setters
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Integer quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    // Constructors
    public ServiceItem() {}

    public ServiceItem(Long serviceId, Long itemId, Integer quantityUsed) {
        this.serviceId = serviceId;
        this.itemId = itemId;
        this.quantityUsed = quantityUsed;
    }

    // Equals and HashCode for composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceItem)) return false;
        ServiceItem that = (ServiceItem) o;
        return serviceId.equals(that.serviceId) && itemId.equals(that.itemId);
    }

    @Override
    public int hashCode() {
        int result = serviceId.hashCode();
        result = 31 * result + itemId.hashCode();
        return result;
    }
}