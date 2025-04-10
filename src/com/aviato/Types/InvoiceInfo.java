package com.aviato.Types;

import java.sql.Date;

public class InvoiceInfo {
    private Long invoiceId;
    private Date invoiceDate;
    private Double totalAmount;
    private String custName;
    private String address;
    private String email;
    private String contact;
    private String licencePlate;
    private String description;

    public InvoiceInfo() {}

    public InvoiceInfo(Long invoiceId, Date invoiceDate, Double totalAmount,
                       String custName, String address, String email, String contact,
                       String licencePlate, String description) {
        this.invoiceId = invoiceId;
        this.invoiceDate = invoiceDate;
        this.totalAmount = totalAmount;
        this.custName = custName;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.licencePlate = licencePlate;
        this.description = description;
    }

    // Getters and Setters
    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }

    public Date getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(Date invoiceDate) { this.invoiceDate = invoiceDate; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getCustName() { return custName; }
    public void setCustName(String custName) { this.custName = custName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getLicencePlate() { return licencePlate; }
    public void setLicencePlate(String licencePlate) { this.licencePlate = licencePlate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
