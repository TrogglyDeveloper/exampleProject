package com.troggly.apiObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vlad on 28.07.2017.
 */
@XmlRootElement
public class LoginRequest {
    @XmlElement(required = true)
    public String login;
    @XmlElement(required = true)
    public String password;
}
