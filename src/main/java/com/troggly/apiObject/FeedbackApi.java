package com.troggly.apiObject;

import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FeedbackApi {//TODO USE for control-pane

    @XmlElement(required = false)
    public String fullName;

    @XmlElement(required = false)
    public String email;

    @XmlElement(required = false)
    public String message;

    @XmlElement(required = false)
    public MultipartFile file;

}
