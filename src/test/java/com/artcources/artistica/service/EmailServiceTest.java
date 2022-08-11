package com.artcources.artistica.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Properties;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    private EmailService toTest;
    private TemplateEngine templateEngine;
    @Mock
    private MessageSource messageSource;
    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        templateEngine = new TemplateEngine();
        toTest = new EmailService(templateEngine, messageSource, javaMailSender);
    }

    @Test
    void sendContactEmailTest() {
        MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        toTest.sendContactEmail("userEmail", "userName", "subject", "messageBody");
        verify(javaMailSender, times(1)).send(mimeMessage);
    }


    @Test
    void sendRegistrationEmailTest() {
        MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(messageSource.getMessage("registration_subject", new Object[0], Locale.ROOT)).thenReturn("subject");
        toTest.sendRegistrationEmail("userEmail", "userName", Locale.ROOT);
        verify(javaMailSender, times(1)).send(mimeMessage);
    }
}
