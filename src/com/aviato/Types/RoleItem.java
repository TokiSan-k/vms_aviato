package com.aviato.Types;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class RoleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "role_name")
    private String roleName;

    // Default constructor
    public RoleItem() {}

    // Parameterized constructor
    public RoleItem(Integer employeeId, String username, String email, String roleName) {
        this.employeeId = employeeId;
        this.username = username;
        this.email = email;
        this.roleName = roleName;
    }

    // Getters and Setters
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
