package com.troggly.service;

import com.troggly.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("fileService")
@PropertySource("classpath:file-server.properties")
public class FileService {
    @Autowired
    private Environment env;

    private static final Logger logger =  LoggerFactory.getLogger(FileService.class);


    public void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(env.getProperty("directory") + file.getOriginalFilename());
            Files.write(path, bytes);

        }
    }
    public List<Image> saveUploadedFilesRetornImages(List<MultipartFile> files) throws IOException {//TODO files logic validate
        List<Image> imageList = null;
        Image image = null;
        logger.error("SIZE FILES"+files.size());
        int i = 0;
        try{
            imageList = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                logger.error("!I"+i);
                continue; //next pls

            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(env.getProperty("directory") + "\\"+file.getOriginalFilename());
            Files.write(path, bytes);
            image  = new Image();
            image.setName(file.getOriginalFilename());
            image.setDate(new Date());
            image.setLink(env.getProperty("prefix") + file.getOriginalFilename());// TODO server logic
            imageList.add(image);
            logger.error("dir "+env.getProperty("directory"));
        }}catch (Exception e){e.printStackTrace();}

        return imageList;
    }



    public Image saveFileWithReturnImage(MultipartFile file) throws IOException {//TODO files logic validate
     Image rezulImage = null;
        Image image = null;
        int i = 0;
        try{
            if (file.isEmpty()) {
                    logger.error("!I"+i);
                    return null;
                }
                byte[] bytes = file.getBytes();
                Path path = Paths.get(env.getProperty("directory") + "\\"+file.getOriginalFilename());
                Files.write(path, bytes);
                image  = new Image();
                image.setName(file.getOriginalFilename());
                image.setDate(new Date());
                image.setLink(env.getProperty("prefix") + file.getOriginalFilename());// TODO server logic
                rezulImage = image;
                logger.error("dir "+env.getProperty("directory"));
            }catch (Exception e){e.printStackTrace();}

        return rezulImage;
    }
}
