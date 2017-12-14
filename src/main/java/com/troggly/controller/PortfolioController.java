package com.troggly.controller;

import com.troggly.apiObject.*;
import com.troggly.mapper.PortfolioMapper;
import com.troggly.model.Image;
import com.troggly.model.Portfolio;
import com.troggly.service.FileService;
import com.troggly.service.PortfolioService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class PortfolioController {
    private static final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioMapper portfolioMapper;

    @Autowired
    private FileService fileService;

    @RequestMapping(path = "/portfolio/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PortfolioList getAllPortfolio() {
        PortfolioList portfolioList = new PortfolioList();
//        portfolioList.portfolios = portfolioService.findAll();
        List<Portfolio> list = portfolioService.findAll();
        for(Portfolio portfolio : list){
            portfolioList.portfolios.add(portfolioMapper.fromInternal(portfolio));
        }
        logger.info("SIZE List"+list.size());
        logger.debug("SIZE List Po"+list.get(0).getImages().size());
        return portfolioList;
    }

    @RequestMapping(path = "/portfolio/getTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ProjectTypes getAllTypes() {
        ProjectTypes projectTypes = null;

        try{
            List<String> types = portfolioService.getTypes();
            projectTypes = new ProjectTypes();
            Type type = null;
            for(String str : types){
                type = new Type();
                type.name = str;
                type.quantity = portfolioService.getPortfolioByType(str).size();
                projectTypes.types.add(type);
            }
        }catch (Exception e){
            projectTypes =  new ProjectTypes();
            projectTypes.errorMessage = e.getMessage();
            projectTypes.returnedCode = -1;
            e.printStackTrace();
            return projectTypes;
        }
        return projectTypes;
    }

//    @RequestMapping(path = "/portfolio/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public PortfolioList addNewPortfolio(@RequestBody PortfolioReply portfolioReply) {
//        PortfolioList portfolioList = null;
//        try {
//            logger.error("name "+portfolioReply.images.size());
//            Portfolio portfolio = portfolioService.save(portfolioMapper.toInternal(portfolioReply));
//            portfolioList = new PortfolioList();
//            portfolioList.portfolios.add(portfolioMapper.fromInternal(portfolio));
//        }catch (Exception e){
//            e.printStackTrace();
//            portfolioList = new PortfolioList();
//            portfolioList.returnedCode = -1;
//            portfolioList.errorMessage = e.getMessage();
//        }
//
//
//        return portfolioList;
//    }


    @RequestMapping(path="/portfolio/del/{portfolioId}",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MainReply delPortfolio(@PathVariable Long portfolioId ){
        MainReply mainReply = null;
        try{
        portfolioService.delete(portfolioId);
        mainReply = new MainReply();
        }catch (Exception e){
            mainReply = new MainReply();
            mainReply.returnedCode = -1;
            mainReply.errorMessage = e.getMessage();
        }
        return mainReply;
    }

//    @RequestMapping(path="/portfolio/del/{portfolioId}",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public MainReply findByPortfolio(@PathVariable Long portfolioId ){
//
//    }
// TODO findPortfolio

   // @RequestMapping(path="/portfolio/file/",  method=RequestMethod.POST)
//   @PostMapping("/portfolio/file/")
//    public MainReply uploadFileMulti(
//           @RequestParam("file") MultipartFile file) {
//
//           try{
//               List<MultipartFile> list = new ArrayList<>();
//               list.add(file);
//               saveUploadedFiles(list);
//               logger.error("file norm" + file.getOriginalFilename());
//         }catch (Exception e){
//               MainReply mainReply = new MainReply();
//               mainReply.errorMessage = e.getMessage();
//               mainReply.returnedCode = -1;
//               return mainReply;
//           }
//
//        return new MainReply();
//    }

    @PostMapping("/portfolio/add")
    public MainReply addNewPortfolio(
            @RequestParam("file") MultipartFile[] files, @RequestParam("fullName") String fullName,
            @RequestParam("description") String description, @RequestParam("projectReference") String projectReference,
            @RequestParam("technology") String technology, @RequestParam("developmentTime") String developmentTime,
            @RequestParam("projectType") String projectType,@RequestParam("date") String stringDate,
            @RequestParam("mainImage") MultipartFile mainImage) {

        try{
            logger.info("MAIN IMAGE NAME +"+mainImage.getOriginalFilename());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
            Date date = sdf.parse(stringDate);
            List<MultipartFile> fileList = new ArrayList<>();
            for (MultipartFile file: files) {
                fileList.add(file);
            }
            List<Image> imageList = fileService.saveUploadedFilesRetornImages(fileList);
            Set<Image> setList = new HashSet<>(imageList);
            Portfolio portfolio = new Portfolio();
            portfolio.setImages(setList);
            Image myMainImage = fileService.saveFileWithReturnImage(mainImage);
            portfolio.setMainImage(myMainImage);//TODO mainImage
            portfolio.setTechnology(technology);
            portfolio.setProjectReference(projectReference);
            portfolio.setFullName(fullName);
            portfolio.setDescription(description);
            //portfolio.setDate(new Date());
            portfolio.setDate(date);
            portfolio.setDevelopmentTime(developmentTime);
            portfolio.setProjectType(projectType);
            portfolioService.save(portfolio);

        }catch (Exception e){
            MainReply mainReply = new MainReply();
            mainReply.errorMessage = e.getMessage();
            mainReply.returnedCode = -1;
            e.printStackTrace();
            return mainReply;

        }

        return new MainReply();
    }
//    @PostMapping("/portfolio/files/")
//    public MainReply uploadFileMultis(
//            @ModelAttribute TestPortfolio portfolio) {
//
//        try{
//            for (MultipartFile file: portfolio.images) {
//                List<MultipartFile> list = new ArrayList<>();
//                list.add(file);
//                saveUploadedFiles(list);
//                logger.error("file norm" + file.getOriginalFilename());
//            }
////            List<MultipartFile> list = new ArrayList<>();
////            list.add(file);
////            saveUploadedFiles(list);
////            logger.error("file norm" + file.getOriginalFilename());
//        }catch (Exception e){
//            MainReply mainReply = new MainReply();
//            mainReply.errorMessage = e.getMessage();
//            mainReply.returnedCode = -1;
//            return mainReply;
//        }
//
//        return new MainReply();
//    }

//    private static String UPLOADED_FOLDER = "/home/vladyslav/Prog/";
//
//    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
//
//        for (MultipartFile file : files) {
//
//            if (file.isEmpty()) {
//                continue; //next pls
//            }
//
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
//            Files.write(path, bytes);
//
//        }
//}


//    @GetMapping(value = "/image")
//    public @ResponseBody byte[] getImage() throws IOException {
//        InputStream in = getClass()
//                .getResourceAsStream("/com/baeldung/produceimage/image.jpg");
//        return IOUtils.toByteArray(in);
//    }
}
