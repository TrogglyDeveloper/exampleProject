package com.troggly.apiObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ProjectTypes extends MainReply{
    @XmlElement(required = false)
    public List<Type> types = new ArrayList<>();
}
