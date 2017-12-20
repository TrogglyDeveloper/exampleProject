package com.troggly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("emailService")
public class EmailService {


    @Autowired
    private EmailService emailService;

    @Autowired
    private TemplateEngine templateEngine;

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }


    public void sendHtml(String html,String to, String subject){
        try{
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            javaMailSender.send(mail);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendHtml(String html,String to, String subject, String fileDirectory, String fileName){
        try{
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            FileSystemResource file
                    = new FileSystemResource(new File(fileDirectory));
            helper.addAttachment(fileName, file);

            javaMailSender.send(mail);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void send(String to, String subject, String templateName, Context context) {
        String body = templateEngine.process(templateName, context);
        sendHtml(body,to,subject);
        // return emailSender.sendHtml(to, subject, body);
    }

    public void send(String to, String subject, String templateName, Context context,String fileDirectory,String fileName) {
        String body = templateEngine.process(templateName, context);
        sendHtml(body,to,subject,fileDirectory,fileName);
        // return emailSender.sendHtml(to, subject, body);
    }

