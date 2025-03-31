package com.aviato.Types;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "SERVICE")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "InsertService",
                procedureName = "add_service",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_service_type", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_service_date", type = Date.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_status", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cost", type = Double.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_service_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetService",
                procedureName = "get_service",
                resultClasses = Service.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_service_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_service_id_out", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "UpdateService",
                procedureName = "update_service",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_service_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_status", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cost", type = Double.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_service_id_out", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "DeleteService",
                procedureName = "delete_service",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_service_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_service_id_out", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetAllServices",
                procedureName = "get_service",
                resultClasses = Service.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_service_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_service_id_out", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "UseInventory",
                procedureName = "use_inventory",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_service_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_item_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_quantity_used", type = Integer.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetServiceInventory",
                procedureName = "get_service_inventory",
                resultClasses = ServiceItem.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_service_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "DeleteServiceInventory",
                procedureName = "delete_service_inventory",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_service_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_item_id", type = Long.class)
                }
        )
})
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "svc_seq")
    @SequenceGenerator(name = "svc_seq", sequenceName = "service_seq", allocationSize = 1)
    @Column(name = "SERVICE_ID")
    private Long serviceId;

    @Column(name = "SERVICE_TYPE")
    private String serviceType;

    @Column(name = "SERVICE_DATE")
    private Date serviceDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "COST")
    private Double cost;

    // Getters and Setters
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    // Constructors
    public Service() {}

    public Service(String serviceType, Date serviceDate, String status, Double cost) {
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.status = status;
        this.cost = cost;
    }
}