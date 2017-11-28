package com.troggly.controller;

import com.troggly.apiObject.MainReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FeedbackController {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @PostMapping("/feedback/add")
    public MainReply addNewPortfolio(
            @RequestParam("file") MultipartFile file, @RequestParam("fullName") String fullName,
            @RequestParam("templates/email") String email, @RequestParam("message") String message) {

        try{
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
//            Date date = sdf.parse(stringDate);
            logger.info("FILE "+file.getOriginalFilename());
            logger.info("templates/email " +email);
        }catch (Exception e){
            MainReply mainReply = new MainReply();
            mainReply.errorMessage = e.getMessage();
            mainReply.returnedCode = -1;
            e.printStackTrace();
            return mainReply;

        }

        return new MainReply();
    }


//    @PostMapping("/feedback/addObject")
//    public MainReply addNewPortfolio(
//            @ModelAttribute FeedbackApi feedbackApi) {
//
//        try{
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
////            Date date = sdf.parse(stringDate);
//            logger.info("FILE "+feedbackApi.file.getOriginalFilename());
//            logger.info("email "+feedbackApi.email);
//        }catch (Exception e){
//            MainReply mainReply = new MainReply();
//            mainReply.errorMessage = e.getMessage();
//            mainReply.returnedCode = -1;
//            e.printStackTrace();
//            return mainReply;
//
//        }
//
//        return new MainReply();
//    }

}
