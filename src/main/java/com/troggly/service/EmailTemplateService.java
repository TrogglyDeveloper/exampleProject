package com.troggly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service("emailTemplateService")
@PropertySource("classpath:file-server.properties")
public class EmailTemplateService {

    @Autowired
    private Environment env;

    @Autowired
    private EmailService emailService;

    public void sendRequestConfirmEmail(String userName, String hash, String emailAddress) throws Exception{

            String subject = "Confirm your email address"; //TODO properties
            Context context = new Context();
            context.setVariable("name", userName);
            context.setVariable("hash", hash);
//        context.setVariable("description", env.getProperty("sora"));
            emailService.send(emailAddress, subject, "templates/email/confirm-email-template.html", context);

    }

    public void sendCompliteRegistration(String userName, String emailAddress) throws Exception{ //TODO может стоит упомянуть о проекте внутри сообщения

            String subject = "Welcome"; //TODO properties
            Context context = new Context();
            context.setVariable("name", userName);
            emailService.send(emailAddress, subject, "templates/email/registration-template.html", context);

    }

    public void sendFeedback(String userName, String emailAddress) throws Exception{ //TODO может стоит упомянуть о проекте внутри сообщения

        String subject = "Welcome"; //TODO properties
        Context context = new Context();
        context.setVariable("name", userName);
        emailService.send(emailAddress, subject, "templates/email/feedback-template.html", context);

    }
    public void sendFeedbackForManagers(String fileUrl,String fileName, String fullName, String email, String message) throws Exception{ //TODO может стоит упомянуть о проекте внутри сообщения

        String subject = "New Feedback from "+fullName; //TODO properties
        Context context = new Context();
        context.setVariable("fullName", fullName);
        context.setVariable("email", email);
        context.setVariable("message", message);
        String emailManagers = env.getProperty("managerEmails");
        String[] emails = emailManagers.split(",");
        for(String emailManager : emails){
        if(fileName!=null) {
            emailService.send(emailManager, subject, "templates/email/feedback-manager-template.html", context, fileUrl, fileName);
        }else {
            emailService.send(emailManager, subject, "templates/email/feedback-manager-template.html", context);
        }
        }

    }

}
