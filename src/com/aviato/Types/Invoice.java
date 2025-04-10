package com.aviato.Types;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "INVOICE")
public class Invoice {
    @Id
    @Column(name = "INVOICE_ID")
    private Long invoiceId;

    @Column(name = "SERVICE_ID")
    private Long serviceId;

    @Column(name = "APP_ID")
    private Long appId;

    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    public Invoice() {}

    public Invoice(Long invoiceId, Long serviceId, Long appId, Date invoiceDate, Double totalAmount) {
        this.invoiceId = invoiceId;
        this.serviceId = serviceId;
        this.appId = appId;
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
    }

    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public Long getAppId() { return appId; }
    public void setAppId(Long appId) { this.appId = appId; }

    public Date getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(Date invoiceDate) { this.invoiceDate = invoiceDate; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
}