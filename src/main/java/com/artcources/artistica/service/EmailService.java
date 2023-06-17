package com.artcources.artistica.service;

import com.artcources.artistica.repository.WorkshopRepository;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailService {

    private final TemplateEngine templateEngine;
    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;


    public EmailService(TemplateEngine templateEngine,
                        MessageSource messageSource,
                        JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.messageSource = messageSource;
        this.javaMailSender = javaMailSender;

    }

    public void sendContactEmail(String userEmail, String userName, String subject, String messageBody) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("artistica.study@gmail.com");
            mimeMessageHelper.setTo("artistica.study@gmail.com");
            mimeMessageHelper.setSubject("New contact mail: " + subject);
            mimeMessageHelper.setText(generateContactMessageContent(userEmail, subject, userName, messageBody), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getEmailSubject(Locale locale) {
        return messageSource.getMessage(
                "registration_subject",
                new Object[0],
                locale);
    }

    private String generateRegistrationMessageContent(Locale locale,
                                                      String userName) {
        Context ctx = new Context();
        ctx.setLocale(locale);
        ctx.setVariable("userName", userName);
        return templateEngine.process("email/registration", ctx);
    }

    private String generateContactMessageContent(String from, String subject, String username, String message) {
        Context ctx = new Context();
        ctx.setVariable("username", username);
        ctx.setVariable("from", from);
        ctx.setVariable("subject", subject);
        ctx.setVariable("message", message);
        return templateEngine.process("email/contact", ctx);
    }

//    public void sendRegistrationEmail(
//            String userEmail,
//            String userName,
//            Locale preferredLocale
//    ) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//            mimeMessageHelper.setFrom("artistica.study@gmail.com");
//            mimeMessageHelper.setTo(userEmail);
//            mimeMessageHelper.setSubject(getEmailSubject(preferredLocale));
//            mimeMessageHelper.setText(generateRegistrationMessageContent(preferredLocale, userName), true);
//
//            javaMailSender.send(mimeMessageHelper.getMimeMessage());
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void sendWeeklyEmail(
            String userName,
            String fullName
    ) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("artistica.study@gmail.com");
            mimeMessageHelper.setTo(userName);
            mimeMessageHelper.setSubject("Hello "+ fullName + ", here are ours most popular workshops");
            mimeMessageHelper.setText(generateWeeklyMessageContent(fullName), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateWeeklyMessageContent(String userName) {
        Context ctx = new Context();
        ctx.setLocale(Locale.ENGLISH);
        ctx.setVariable("userName", userName);
        return templateEngine.process("email/weekly", ctx);
    }

}