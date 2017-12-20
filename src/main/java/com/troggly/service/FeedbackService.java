package com.troggly.service;

import com.troggly.model.Feedback;
import com.troggly.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:file-server.properties")
public class FeedbackService extends GeneralServiceImp<Feedback,Long> {

    @Autowired
    private Environment env;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Override
    public JpaRepository<Feedback, Long> getRepository() {
        return feedbackRepository;
    }

    public Feedback addFeedback(MultipartFile file, String fullName, String email, String message) throws Exception{
        Feedback feedbackReturn = null;
        String fileDirectory = null;
        String fileName = null;
        Feedback feedback = new Feedback();
        feedback.setEmail(email);
        feedback.setFullName(fullName);
        feedback.setMessage(message);
        if(file!=null){

        String fileUrl = env.getProperty("directory") + file.getOriginalFilename();
        feedback.setFileUrl(fileUrl);
        fileDirectory = fileUrl;
        fileName = file.getOriginalFilename();
        List<MultipartFile> multipartFileList = new ArrayList<>();
        multipartFileList.add(file);
        fileService.saveUploadedFiles(multipartFileList);
        }
        feedbackReturn = feedbackRepository.save(feedback);

        emailTemplateService.sendFeedback(fullName,email);

        emailTemplateService.sendFeedbackForManagers(fileDirectory,fileName,fullName,email,message);

        return feedbackReturn;
    }


}
