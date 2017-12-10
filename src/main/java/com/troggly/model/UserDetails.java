package com.troggly.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Vlad on 10.05.2017.
 */
@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 5744170739949338983L;

    @Id
    @NotNull
    @Size(max = 68)
    @Column(name = "id_details")
    private String id;

    @NotNull
    @Size(max = 50)
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Column(name = "second_name")
    private String secondName;

    //    @NotNull
    @Size(max = 50)
    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Size(max = 200)
    @Column(name = "address")
    private String address;

//    @ElementCollection(targetClass = String.class)
//    @CollectionTable(name = "emails")
//    @Column(name = "email")
//    private List<String> emails;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<UserEmail> emails = new HashSet<>();

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "telephones")
    @Column(name = "telephone")
    private List<String> telephones;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "skype_array")
    @Column(name = "skype")
    private List<String> skype_array;
    @Size(max = 250)
    @Column(name = "additional_information")
    private String additionalInformation;
//    @Size(max = 40) @Size(max = 40)
    @Column(name = "zip_code")
    private Integer zipCode;
    @Size(max = 50)
    @Column(name = "country")
    private String country;
    @Size(max = 90)
    @Column(name = "company")
    private String company;

    public UserDetails() {
    }

    public UserDetails(String id, String firstName, String secondName, String middleName, String address, List<String> emails, List<String> telephones, List<String> skype_array) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.address = address;
//        this.emails = emails;
        this.telephones = telephones;
        this.skype_array = skype_array;
    }

    public UserDetails(String id, String firstName, String secondName, String address, List<String> emails, List<String> telephones, List<String> skype_array) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.address = address;
//        this.emails = emails;
        this.telephones = telephones;
        this.skype_array = skype_array;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
//
//    public List<String> getEmails() {
//        return emails;
//    }
//
//    public void setEmails(List<String> emails) {
//        this.emails = emails;
//    }


    public Set<UserEmail> getEmails() {
        return emails;
    }

    public void setEmails(Set<UserEmail> emails) {
        this.emails = emails;
    }

    public List<String> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<String> telephones) {
        this.telephones = telephones;
    }

    public List<String> getSkype_array() {
        return skype_array;
    }

    public void setSkype_array(List<String> skype_array) {
        this.skype_array = skype_array;
    }


    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", address='" + address + '\'' +
//                ", emails=" + emails +
                ", telephones=" + telephones +
                ", skype_array=" + skype_array +
                ", additionalInformation='" + additionalInformation + '\'' +
                ", zipCode=" + zipCode +
                ", country='" + country + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetails that = (UserDetails) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (secondName != null ? !secondName.equals(that.secondName) : that.secondName != null) return false;
        if (middleName != null ? !middleName.equals(that.middleName) : that.middleName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (emails != null ? !emails.equals(that.emails) : that.emails != null) return false;
        if (telephones != null ? !telephones.equals(that.telephones) : that.telephones != null) return false;
        if (skype_array != null ? !skype_array.equals(that.skype_array) : that.skype_array != null) return false;
        if (additionalInformation != null ? !additionalInformation.equals(that.additionalInformation) : that.additionalInformation != null)
            return false;
        if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        return company != null ? company.equals(that.company) : that.company == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (emails != null ? emails.hashCode() : 0);
        result = 31 * result + (telephones != null ? telephones.hashCode() : 0);
        result = 31 * result + (skype_array != null ? skype_array.hashCode() : 0);
        result = 31 * result + (additionalInformation != null ? additionalInformation.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        return result;
    }
}
