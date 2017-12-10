package com.troggly.apiObject;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

/**
 * Created by Vlad on 26.07.2017.
 */
@XmlRootElement
public class UserDetailsApi {

    @XmlElement(required = false)
    public String id;

    @XmlElement(required = true)
    public String firstName;

    @XmlElement(required = true)
    public String secondName;

    @XmlElement(required = true)
    public String middleName;

    @XmlElement(required = false)
    public String address;

    @XmlElement(required = false)
    public List<String> emails;

    @XmlElement(required = false)
    public List<String> telephones;

    @XmlElement(required = false)
    public List<String> skype_array;

    @XmlElement(required = false)
    public String additionalInformation;

    @XmlElement(required = false)
    public Integer zipCode;

    @XmlElement(required = false)
    public String country;

    @XmlElement(required = false)
    public String company;
}
