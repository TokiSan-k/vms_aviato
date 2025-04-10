package com.aviato.Types;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "USER")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "InsertUser",
                procedureName = "user_procedures.add_user",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_password", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_username", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_role_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_user_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetUser",
                procedureName = "user_procedures.get_user",
                resultClasses = User.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "UpdateUser",
                procedureName = "user_procedures.update_user",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_password", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_username", type = String.class), // Added missing p_username
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_role_id", type = Long.class)    // Added missing p_role_id
                }
        ),
        @NamedStoredProcedureQuery(
                name = "DeleteUser",
                procedureName = "user_procedures.delete_user",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetAllUsers",
                procedureName = "user_procedures.get_user",
                resultClasses = User.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "AuthenticateUser",
                procedureName = "authenticate_user",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_password", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_role_name", type = String.class)
                }
        )
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usr_seq")
    @SequenceGenerator(name = "usr_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "ROLE_ID")
    private Long roleId;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void SetAllFields(String email, String password, String username, Long roleId){
        this.email = email;
        this.password = password;
        this.username = username;
        this.roleId = roleId;
    }

    // Constructors
    public User() {}

    public User(String email, String password, String username, Long roleId) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.roleId = roleId;
    }
}