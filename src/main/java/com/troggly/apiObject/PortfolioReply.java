package com.troggly.apiObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
public class PortfolioReply {

    @XmlElement(required = false)
    public Integer id;

    @XmlElement(required = false)
    public Date date;

    @XmlElement(required = true)
    public ImageReply mainImage; //good

    @XmlElement(required = true)
    public String fullName;

    @XmlElement(required = true)
    public String description;

    @XmlElement(required = false)
    public String projectReference;

    @XmlElement(required = true)
    public String technology;

    @XmlElement(required = false)
    public String projectType;

    @XmlElement(required = false)
    public String developmentTime;

    @XmlElement(required = true)
    public Set<ImageReply> images = new HashSet<>(); //TODO
}
