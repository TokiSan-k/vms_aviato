package com.aviato.Types;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "APPOINTMENT")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "ScheduleAppointment",
                procedureName = "appointment_procedures.schedule_appointment",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_vehicle_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_app_date", type = Date.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_app_time", type = Timestamp.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_status", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_service_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_emp_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_app_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetAppointment",
                procedureName = "appointment_procedures.get_appointment",
                resultClasses = Appointment.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_app_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "UpdateAppointmentStatus",
                procedureName = "appointment_procedures.update_appointment_status",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_app_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_status", type = String.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "DeleteAppointment",
                procedureName = "appointment_procedures.delete_appointment",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_app_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetAllAppointments",
                procedureName = "appointment_procedures.get_appointment",
                resultClasses = Appointment.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_app_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GenerateInvoice",
                procedureName = "generate_invoice",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_app_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_description", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_total_amount", type = Double.class),

                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_invoice_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_invoice_date", type = java.sql.Date.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_total_amount_out", type = Double.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_cust_name", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_address", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_contact", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_licence_plate", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_description_out", type = String.class)
                }
        )

})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_seq")
    @SequenceGenerator(name = "app_seq", sequenceName = "appointment_seq", allocationSize = 1)
    @Column(name = "APP_ID")
    private Long appId;

    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "VEHICLE_ID")
    private Long vehicleId;

    @Column(name = "APP_DATE")
    private Date appDate;

    @Column(name = "APP_TIME")
    private Timestamp appTime;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SERVICE_ID")
    private Long serviceId;

    @Column(name = "EMP_ID")
    private Long empId;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Timestamp getAppTime() {
        return appTime;
    }

    public void setAppTime(Timestamp appTime) {
        this.appTime = appTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Appointment() {}

    public Appointment(Long custId, Long vehicleId, Date appDate, Timestamp appTime, String status, Long serviceId, Long empId) {
        this.custId = custId;
        this.vehicleId = vehicleId;
        this.appDate = appDate;
        this.appTime = appTime;
        this.status = status;
        this.serviceId = serviceId;
        this.empId = empId;
    }
}