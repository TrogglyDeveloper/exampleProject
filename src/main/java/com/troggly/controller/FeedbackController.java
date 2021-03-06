package com.troggly.controller;

import com.troggly.apiObject.MainReply;
import com.troggly.service.FeedbackService;
import com.troggly.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FeedbackController {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/feedback/add")
    public MainReply addNewPortfolio(
            @RequestParam("file") MultipartFile file, @RequestParam("fullName") String fullName,
            @RequestParam("email") String email, @RequestParam("message") String message) {

        try{
            feedbackService.addFeedback(file,fullName,email,message);

        }catch (Exception e){
            MainReply mainReply = new MainReply();
            mainReply.errorMessage = e.getMessage();
            mainReply.returnedCode = -1;
            e.printStackTrace();
            return mainReply;

        }

        return new MainReply();
    }

}
