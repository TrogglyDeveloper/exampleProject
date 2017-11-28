package com.troggly.apiObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by Vlad on 26.07.2017.
 */
@XmlRootElement
public class UserApi {

    @XmlElement(required = true)
    public String login;

    @XmlElement(required = true)
    public String type;

    @XmlElement(required = true)
    public Date date;

    @XmlElement(required = true)
    public List<String> roles;

    @XmlElement(required = true)
    public UserDetailsApi userDetails;
}
