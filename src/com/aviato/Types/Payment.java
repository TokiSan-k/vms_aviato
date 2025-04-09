package com.aviato.Types;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PAYMENT")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "RecordPayment",
                procedureName = "payment_pkg.record_payment",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_invoice_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_amount_paid", type = BigDecimal.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_payment_method", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_status", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_payment_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetPaymentById",
                procedureName = "payment_pkg.get_payment_by_id",
                resultClasses = Payment.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_payment_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "UpdatePaymentStatus",
                procedureName = "payment_pkg.update_payment_status",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_payment_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_new_status", type = String.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "getAllPayment",
                procedureName = "get_all_payments",
                resultClasses = Payment.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        )
})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    @Column(name = "INVOICE_ID")
    private Long invoiceId;

    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(name = "AMOUNT_PAID")
    private BigDecimal amountPaid;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "STATUS")
    private String status;

    // Getters and Setters
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void SetAllFields(Long invoiceId, Date paymentDate, BigDecimal amountPaid, String paymentMethod, String status){
        this.invoiceId = invoiceId;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public Payment() {}

    public Payment(Long invoiceId, Date paymentDate, BigDecimal amountPaid, String paymentMethod, String status) {
        this.invoiceId = invoiceId;
        this.paymentDate = paymentDate;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }
}