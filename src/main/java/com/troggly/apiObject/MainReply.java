package com.troggly.apiObject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vlad on 28.07.2017.
 */
@XmlRootElement
public class MainReply {

    @XmlElement(required = true)
    public Integer returnedCode = 0;

    @XmlElement(required = true)
    public String apiVer = "0.0.1";

    @XmlElement(required = false)
    public String errorMessage;
}
