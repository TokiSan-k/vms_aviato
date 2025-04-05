package com.aviato.Types;

import javax.persistence.*;

@Entity
@Table(name = "VEHICLE")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "InsertVehicle",
                procedureName = "vehicle_procedures.add_vehicle",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_licence_plate", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_make", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_model", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_vehicle_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetVehicle",
                procedureName = "vehicle_procedures.get_vehicle",
                resultClasses = Vehicle.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_vehicle_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "UpdateVehicle",
                procedureName = "vehicle_procedures.update_vehicle",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_vehicle_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_licence_plate", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_make", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_model", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_year", type = Integer.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "DeleteVehicle",
                procedureName = "vehicle_procedures.delete_vehicle",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_vehicle_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetAllVehicles",
                procedureName = "vehicle_procedures.get_vehicle",
                resultClasses = Vehicle.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_vehicle_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        )
})
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_seq")
    @SequenceGenerator(name = "vehicle_seq", sequenceName = "vehicle_seq", allocationSize = 1)
    @Column(name = "VEHICLE_ID")
    private Long vehicleId;

    @Column(name = "CUST_ID")
    private Long customerId;

    @Column(name = "LICENCE_PLATE")
    private String licencePlate;

    @Column(name = "MAKE")
    private String make;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "YEAR")
    private int year;

    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId;}

    public String getLicencePlate() { return licencePlate; }
    public void setLicencePlate(String licencePlate) { this.licencePlate = licencePlate; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make;}

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model;}

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year;}

    public void SetAllFields(Long customerId, String licencePlate, String make, String model, int year)
    {
        this.customerId = customerId;
        this.licencePlate = licencePlate;
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public Vehicle() {}

    public Vehicle(Long customerId, String licencePlate, String make, String model, int year) {
        this.customerId = customerId;
        this.licencePlate = licencePlate;
        this.make = make;
        this.model = model;
        this.year = year;
    }
}