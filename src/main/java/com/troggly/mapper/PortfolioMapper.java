package com.troggly.mapper;

import com.troggly.apiObject.ImageReply;
import com.troggly.apiObject.PortfolioReply;
import com.troggly.model.Image;
import com.troggly.model.Portfolio;
import com.troggly.repository.PortfolioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class PortfolioMapper {
    private static final Logger logger = LoggerFactory.getLogger(PortfolioMapper.class);

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private PortfolioRepository portfolioRepository;

    public PortfolioReply fromInternal(Portfolio portfolio){
        PortfolioReply portfolioReply = null;
        if (portfolio != null) {
            portfolioReply = new PortfolioReply();
//            userApi.login = user.getLogin();
           portfolioReply.date = portfolio.getDate();
           portfolioReply.description = portfolio.getDescription();
           portfolioReply.fullName = portfolio.getFullName();
           portfolioReply.id = portfolio.getId().intValue();
           portfolioReply.projectReference = portfolio.getProjectReference();
           portfolioReply.technology = portfolio.getTechnology();
           portfolioReply.mainImage = imageMapper.fromInternal(portfolio.getMainImage());
           portfolioReply.developmentTime = portfolio.getDevelopmentTime();
           portfolioReply.projectType = portfolio.getProjectType();
        //   logger.debug("mainImageName : "+portfolioReply.mainImage.name);
            portfolioReply.images = new HashSet<>();
            for (Image image:portfolio.getImages()) {
                    portfolioReply.images.add(imageMapper.fromInternal(image));
            }

          // portfolioReply.mainImage
           // userApi.userDetails = userDetailsMapper.fromInternal(user.getUserDetails());
        }

        return portfolioReply;
    }

    public Portfolio toInternal(PortfolioReply portfolioReply){
        Portfolio portfolio = null;
        if(portfolioReply==null) return null;
        if(portfolioReply.id!=null){
            portfolio = portfolioRepository.findOne(portfolioReply.id.longValue());
        }
        if(portfolioReply.id==null){
            portfolio = new Portfolio();
            portfolio.setDate(portfolioReply.date);
            portfolio.setDescription(portfolioReply.description);
            portfolio.setFullName(portfolioReply.fullName);
            portfolio.setProjectReference(portfolioReply.projectReference);
            portfolio.setTechnology(portfolioReply.technology);
            portfolio.setMainImage(imageMapper.toInternal(portfolioReply.mainImage));
            portfolio.setProjectType(portfolioReply.projectType);
            portfolio.setDevelopmentTime(portfolioReply.developmentTime);
            Set<Image> images = new HashSet<>();
            logger.error("array "+portfolioReply.images.size());
            if(portfolioReply.images!=null){
            for (ImageReply img:portfolioReply.images) {
                images.add(imageMapper.toInternal(img));
            }
            portfolio.setImages(images);
            }
        }
        return portfolio;
    }
}
