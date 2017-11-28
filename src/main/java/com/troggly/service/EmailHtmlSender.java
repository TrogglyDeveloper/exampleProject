package com.troggly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service("emailHtmlSender")
public class EmailHtmlSender {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TemplateEngine templateEngine;

//    @Autowired
//    public EmailHtmlSender (TemplateEngine templateEngine){
//        this.templateEngine = templateEngine;
//    }

    public void send(String to, String subject, String templateName, Context context) {
        String body = templateEngine.process(templateName, context);
        emailService.sendHtml(body);
       // return emailSender.sendHtml(to, subject, body);
    }
}