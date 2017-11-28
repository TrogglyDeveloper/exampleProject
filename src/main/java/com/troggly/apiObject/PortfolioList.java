package com.troggly.apiObject;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class PortfolioList extends MainReply {
    @XmlElement(required = true)
    public List<PortfolioReply> portfolios = new ArrayList<>();
}
