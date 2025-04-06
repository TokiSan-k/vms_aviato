package com.aviato.Types;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "EMPLOYEE")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "InsertEmployee",
                procedureName = "employee_procedures.add_employee",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_emp_name", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_position", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_emp_phn", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_salary", type = Double.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_hire_date", type = Date.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_hours_worked", type = Double.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_emp_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetEmployee",
                procedureName = "employee_procedures.get_employee",
                resultClasses = Employee.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_emp_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "UpdateEmployee",
                procedureName = "employee_procedures.update_employee",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_emp_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_emp_name", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_position", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_emp_phn", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_salary", type = Double.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_hire_date", type = Date.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_hours_worked", type = Double.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "DeleteEmployee",
                procedureName = "employee_procedures.delete_employee",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_emp_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetAllEmployees",
                procedureName = "employee_procedures.get_employee",
                resultClasses = Employee.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_emp_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        )
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
    @SequenceGenerator(name = "emp_seq", sequenceName = "employee_seq", allocationSize = 1)
    @Column(name = "EMP_ID")
    private Long empId;

    @Column(name = "EMP_NAME")
    private String empName;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "EMP_PHN")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SALARY")
    private Double salary;

    @Column(name = "HIRE_DATE")
    private Date hireDate;

    @Column(name = "HOURS_WORKED")
    private Double hoursWorked;

    // Getters and Setters
    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void SetAllFields(String empName, String position, String phone, String email, Double salary, Date hireDate, Double hoursWorked)
    {
        this.empName = empName;
        this.position = position;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.hireDate = hireDate;
        this.hoursWorked = hoursWorked;
    }

    // Constructors
    public Employee() {}

    public Employee(String empName, String position, String phone, String email, Double salary, Date hireDate, Double hoursWorked) {
        this.empName = empName;
        this.position = position;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.hireDate = hireDate;
        this.hoursWorked = hoursWorked;
    }
}