package com.troggly.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Vlad on 03.09.2017.
 */
@Entity
@Table(name = "image")
public class Image implements Serializable {
    private static final long serialVersionUID = -5337298045362610361L;
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 60)
    private String name;

    @NotNull
    private Date date;

    @NotNull
    private String link;
//    @ManyToOne
//    @JoinColumn(name = "portfolio_id")
//    private Portfolio portfolio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//    public Portfolio getPortfolio() {
//        return portfolio;
//    }
//
//    public void setPortfolio(Portfolio portfolio) {
//        this.portfolio = portfolio;
//    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", link='" + link + '\'' +
                '}';
    }
}
