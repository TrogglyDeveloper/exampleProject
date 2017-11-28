package com.troggly.service;

import com.troggly.model.Portfolio;
import com.troggly.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService extends GeneralServiceImp<Portfolio,Long> {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public JpaRepository getRepository() {
        return portfolioRepository;
    }

    public List<String> getTypes(){
        return portfolioRepository.findDistinctProjectType();
    }

    public List<Portfolio> getPortfolioByType(String type){
     return portfolioRepository.findAllByProjectType(type);
    }
}
