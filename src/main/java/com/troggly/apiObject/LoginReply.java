package com.troggly.apiObject;

import com.troggly.apiObject.MainReply;
import com.troggly.apiObject.UserApi;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 28.07.2017.
 */
public class LoginReply extends MainReply {
    @XmlElement
    public String token;
    @XmlElement
    public UserApi user;

    @XmlElement(required = true)
    public List<String> roles = new ArrayList<>();
}
