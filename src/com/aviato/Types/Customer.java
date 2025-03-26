package com.aviato.Types;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@NamedStoredProcedureQueries({
@NamedStoredProcedureQuery(
        name = "InsertCustomer",
        procedureName = "add_customer",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_name", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_phone", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_address", type = String.class)
        }
), @NamedStoredProcedureQuery(
        name = "GetCustomer",
        procedureName = "get_customer",
        resultClasses = Customer.class,  // Maps the result set to Customer objects
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
        }),
        @NamedStoredProcedureQuery(
        name = "UpdateCustomer",
        procedureName = "update_customer",
        resultClasses = Customer.class,  // Maps the result set to Customer objects
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_cust_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
        }),
})
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_seq")
    @SequenceGenerator(name = "cust_seq", sequenceName = "customer_seq", allocationSize = 1)
    private Long custId;

    @Column(name = "cust_name")
    private String Name;

    @Column(name = "phone")
    private String Phone;

    @Column(name = "email")
    private String EmailId;

    @Column(name = "address")
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

    public Customer(String name, String phone, String emailId, String address) {
        this.Name = name;
        this.Phone = phone;
        this.EmailId = emailId;
        this.Address = address;
    }
}