package com.aviato.Types;

import javax.persistence.*;

@Entity
@Table(name = "service_inventory")
public class ServiceItem {

    @Column(name = "service_id")
    private Long serviceId;

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


    public ServiceItem () {}

    public ServiceItem (Long serviceId, Long itemId, Integer quantityUsed) {
        this.serviceId = serviceId;
        this.itemId = itemId;
        this.quantityUsed = quantityUsed;
    }
}