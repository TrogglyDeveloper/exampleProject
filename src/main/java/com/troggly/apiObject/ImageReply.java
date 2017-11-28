package com.troggly.apiObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class ImageReply {

    @XmlElement(required = false)
    public Integer id;

    @XmlElement(required = true)
    public String name;

    @XmlElement(required = false)
    public Date date;

    @XmlElement(required = true)
    public String link;

}
