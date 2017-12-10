package com.troggly.model;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "confirm_email_hashes")
public class ConfirmEmailHashes implements Serializable{

    private static final long serialVersionUID = 4056998918772634021L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hash")
    @Size(max = 500)
    private String hash;

    @OneToOne(cascade = CascadeType.DETACH)
    // @PrimaryKeyJoinColumn
    @JoinColumn(name = "user")
    private User user;


    @OneToOne(cascade = CascadeType.DETACH)
    // @PrimaryKeyJoinColumn
    @JoinColumn(name = "email_id")
    private UserEmail email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserEmail getEmail() {
        return email;
    }

    public void setEmail(UserEmail email) {
        this.email = email;
    }
}
