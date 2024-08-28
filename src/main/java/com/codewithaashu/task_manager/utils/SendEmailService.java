package com.codewithaashu.task_manager.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

//for sending email, we have to install Java Mail Sender dependency
//configuration some settings of java mail sender

@Service
public class SendEmailService {

    // create an object of JavaMailSender
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;
    // for sending an html message
    public void sendEmail(String from, String to, String subject, String templateName, Context context)
            throws MessagingException {
        // create a mime message by using java mail sender
        MimeMessage message = javaMailSender.createMimeMessage();
        // create a mime message helper
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");
        // set the attributes of mime message helper
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        // create body
        String body = templateEngine.process(templateName, context);
        // set the body
        messageHelper.setText(body, true);
        // sende the message
        javaMailSender.send(message);
    }
}
