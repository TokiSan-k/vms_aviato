package com.aviato.Types;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "USER_TABLE")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "InsertUser",
                procedureName = "user_procedures.add_user",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_role_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_username", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_password", type = String.class),
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
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_username", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_password", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_role_id", type = Long.class)
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
        )
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void SetAllFields(Long roleId, String username, String email, String password){
        this.roleId = roleId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Constructors
    public User() {}

    public User(Long roleId, String username, String email, String password) {
        this.roleId = roleId;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}