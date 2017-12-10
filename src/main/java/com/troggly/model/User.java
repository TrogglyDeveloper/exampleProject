package com.troggly.model;

import com.troggly.enums.Role;
import com.troggly.enums.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Vlad on 10.05.2017.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -3305316155315906959L;

    @Id
    @NotNull
    @Size(max = 60)
    @Column(name = "login")
    private String login;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password_hash")
    private String passwordHash;

    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "roles")
    @Column(name = "role")
    private List<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
   // @PrimaryKeyJoinColumn
    @JoinColumn(name = "id")
    private UserDetails userDetails;
//TODO change table name

    public User() {
    }

    public User(String login, String passwordHash, Type type, Date date, List<Role> roles, UserDetails userDetails) {
        this.login = login;
        this.passwordHash = passwordHash;
        this.type = type;
        this.date = date;
        this.roles = roles;
        this.userDetails = userDetails;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", type=" + type +
                ", date=" + date +
                ", roles=" + roles +
                ", userDetails=" + userDetails +
                '}';
    }
}
