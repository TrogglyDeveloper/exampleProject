package com.troggly.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "emails")
public class UserEmail implements Serializable {

    private static final long serialVersionUID = 2863084832594241674L;

    @Id
    @Column(name = "email_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "email")
    @NotNull
    private String email;
//TODO SIZE EMAIL LINE

    @Column(name = "confirm", columnDefinition = "boolean default true", nullable = false)
    private Boolean confirm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }
}
