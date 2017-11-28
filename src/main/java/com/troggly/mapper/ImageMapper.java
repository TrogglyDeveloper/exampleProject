package com.troggly.mapper;

import com.troggly.apiObject.ImageReply;
import com.troggly.apiObject.PortfolioReply;
import com.troggly.model.Image;
import com.troggly.model.Portfolio;
import com.troggly.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    @Autowired
    private PortfolioMapper portfolioMapper;

    @Autowired
    private ImageRepository imageRepository;

    public ImageReply fromInternal(Image image){
        ImageReply imageReply = null;
        if(image!=null){
            imageReply = new ImageReply();
            imageReply.date = image.getDate();
            imageReply.id = image.getId().intValue();
            imageReply.link = image.getLink();
            imageReply.name = image.getName();
        }

        return imageReply;
    }

    public Image toInternal(ImageReply imageReply){
        Image image = null;
        if(imageReply==null) return null;
        if(imageReply.id!=null){
                image = imageRepository.findOne(imageReply.id.longValue());
        }
        if (image == null){
            image = new Image();
            image.setDate(imageReply.date);
            image.setLink(imageReply.link);
            image.setName(imageReply.name);
        }

        return image;
    }
}
