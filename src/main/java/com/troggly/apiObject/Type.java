package com.troggly.apiObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Type {
    @XmlElement(required = false)
    public String name;
    @XmlElement(required = false)
    public int quantity;
}
