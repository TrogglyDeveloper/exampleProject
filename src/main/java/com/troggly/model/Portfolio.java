package com.troggly.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Vlad on 03.09.2017.
 */
@Entity
@Table(name = "portfolio")
public class Portfolio implements Serializable {
    private static final long serialVersionUID = 1879289878797407954L;

    @Id
    @Column(name = "partfolio_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Date date;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_image_id")
    private Image mainImage; //good

    private String fullName;


    @NotNull
    @Size(max = 15000)
    private String description;
    @Size(max = 80)
    private String developmentTime;

    private String projectReference; //link

    private String projectType; //link

    @NotNull
    @Size(max = 300)
    private String technology;

   // @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
   @OneToMany(
           cascade = CascadeType.ALL,
           orphanRemoval = true
   )
    private Set<Image> images = new HashSet<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getProjectReference() {
        return projectReference;
    }

    public void setProjectReference(String projectReference) {
        this.projectReference = projectReference;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    //    public Image getMainImage() {
//        return mainImage;
//    }
//
//    public void setMainImage(Image mainImage) {
//        this.mainImage = mainImage;
//    }

//    public List<Image> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }


    public String getDevelopmentTime() {
        return developmentTime;
    }

    public void setDevelopmentTime(String developmentTime) {
        this.developmentTime = developmentTime;
    }

    public Image getMainImage() {
        return mainImage;
    }

    public void setMainImage(Image mainImage) {
        this.mainImage = mainImage;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}
