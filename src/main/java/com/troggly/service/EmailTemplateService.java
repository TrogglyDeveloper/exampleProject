package com.troggly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service("emailTemplateService")
public class EmailTemplateService {

    @Autowired
    private EmailService emailService;

    public boolean sendRequestConfirmEmail(String userName, String hash, String emailAddress){
        boolean rezult = true;
        try {
            String subject = "Confirm your email address"; //TODO properties
            Context context = new Context();
            context.setVariable("name", userName);
            context.setVariable("hash", hash);
//        context.setVariable("description", env.getProperty("sora"));
            emailService.send(emailAddress, subject, "templates/email/confirm-email-template.html", context);
        }catch (Exception e){
            rezult = false;
            return rezult;
        }
        return rezult;
    }

    public boolean sendCompliteRegistration(String userName, String emailAddress){ //TODO может стоит упомянуть о проекте внутри сообщения
        boolean rezult = true;
        try{
            String subject = "Welcome"; //TODO properties
            Context context = new Context();
            context.setVariable("name", userName);
            emailService.send(emailAddress, subject, "templates/email/registration-template.html", context);
        }catch (Exception e){
            rezult = false;
            return rezult;
        }
        return rezult;
    }
}
