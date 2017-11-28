package com.troggly.apiObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 28.07.2017.
 */
@XmlRootElement
public class UserList extends MainReply {

    @XmlElement(required = true)
    public List<UserApi> users = new ArrayList<>();

}
