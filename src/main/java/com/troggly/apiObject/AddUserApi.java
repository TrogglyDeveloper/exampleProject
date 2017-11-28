package com.troggly.apiObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by Vlad on 17.08.2017.
 */
@XmlRootElement
public class AddUserApi {

    @XmlElement(required = true)
    public String login;

    @XmlElement(required = true)
    public String password;

    @XmlElement(required = true)
    public String type;

    @XmlElement(required = true)
    public Date date;

    @XmlElement(required = false)
    public List<String> roles;

    @XmlElement(required = false)
    public UserDetailsApi userDetails;
}
