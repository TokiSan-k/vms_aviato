package com.aviato.Types;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "InsertCustomer",
                procedureName = "customer_procedures.add_customer",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_name", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phone", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_address", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_cust_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetCustomer",
                procedureName = "customer_procedures.get_customer",
                resultClasses = Customer.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "UpdateCustomer",
                procedureName = "customer_procedures.update_customer",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_name", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phone", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_address", type = String.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "DeleteCustomer",
                procedureName = "customer_procedures.delete_customer",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetAllCustomers",
                procedureName = "customer_procedures.get_customer",
                resultClasses = Customer.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        )
})
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_seq")
    @SequenceGenerator(name = "cust_seq", sequenceName = "customer_seq", allocationSize = 1)
    @Column(name = "CUST_ID")
    private Long custId;

    @Column(name = "CUST_NAME")
    private String Name;

    @Column(name = "PHONE")
    private String Phone;

    @Column(name = "EMAIL")
    private String EmailId;

    @Column(name = "ADDRESS")
    private String Address;


    public Long getCustId(){ return custId;}
    public void setCustId(Long id){ custId = id;}

    public String getName() { return Name;}
    public void setName(String name) { Name = name;}

    public String getPhone() { return Phone;}
    public void setPhone(String phone) { Phone = phone;}

    public String getEmailId() { return EmailId;}
    public void setEmailId(String emailId) { EmailId = emailId;}

    public String getAddress() { return Address;}
    public void setAddress(String address) { Address = address;}

    public void SetAllFields(String fName, String lName, String phone, String emailId, String address)
    {
        Name = fName+" "+lName;
        Phone = phone;
        EmailId = emailId;
        Address = address;
    }

    public Customer(){}

    public Customer(String name, String phone, String emailId, String address) {
        this.Name = name;
        this.Phone = phone;
        this.EmailId = emailId;
        this.Address = address;
    }
}