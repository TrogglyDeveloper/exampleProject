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

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service("emailService")
public class EmailService {




    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }


    public void send(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("sistimas001@gmail.com");
        simpleMailMessage.setFrom("web.studio.eternity@gmail.com");
        simpleMailMessage.setSubject("subject");
        simpleMailMessage.setText("Text");

        javaMailSender.send(simpleMailMessage);
    }

    public void sendHtml(String html){
        try{
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
//        simpleMailMessage.setTo("sistimas001@gmail.com");
//        simpleMailMessage.setFrom("web.studio.eternity@gmail.com");
//        simpleMailMessage.setSubject("subject");
//        simpleMailMessage.setText("Text",true);
            helper.setTo("sistimas001@gmail.com");
            helper.setSubject("subject");
            helper.setText(html, true);

            FileSystemResource file
                    = new FileSystemResource(new File("F:\\загрузкиХрома2\\Article2.pdf"));
            helper.addAttachment("Invoice.pdf", file);

            javaMailSender.send(mail);

    //    javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    @Autowired
//    public JavaMailSenderImpl emailSender;

//    public void sendSimpleMessage(String to, String subject, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        emailSender.send(message);
//
//    }
}
