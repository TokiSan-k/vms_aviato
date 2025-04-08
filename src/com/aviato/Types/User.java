package com.aviato.Types;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "USER")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "InsertUser",
                procedureName = "user_pkg.add_user",
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
                procedureName = "user_pkg.get_user",
                resultClasses = User.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_user_id_out", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "UpdateUser",
                procedureName = "user_pkg.update_user",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_email", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_password", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_user_id_out", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "DeleteUser",
                procedureName = "user_pkg.delete_user",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_user_id_out", type = Long.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "GetAllUsers",
                procedureName = "user_pkg.get_user",
                resultClasses = User.class,
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_user_id", type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "p_cursor", type = void.class),
                        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "p_user_id_out", type = Long.class)
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

    @Column(name = "CREATED_DATE")
    private Date createdDate;

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void SetAllFields(String email, String password, String username, Long roleId, Date createdDate){
        this.email = email;
        this.password = password;
        this.username = username;
        this.roleId = roleId;
        this.createdDate = createdDate;
    }

    // Constructors
    public User() {}

    public User(String email, String password, String username, Long roleId, Date createdDate) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.roleId = roleId;
        this.createdDate = createdDate;
    }
}