package com.oozeander.service.impl;

import java.io.File;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.oozeander.service.MailerService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailerServiceImpl implements MailerService {
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(MailerService.class);
    }

    @Autowired
    private JavaMailSender mailSender;
    @Value("${mailer.user}")
    private String sender;

    @Override
    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(sender);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(text);

        mailSender.send(mail);
    }

    @Override
    public void sendMail(String to, String subject, String text, File[] files) {
        MimeMessage mail = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            Arrays.stream(files).forEach(file -> {
                try {
                    helper.addAttachment(file.getName(), file);
                } catch (MessagingException exception) {
                    LOGGER.error(exception.getLocalizedMessage());
                }
            });
        } catch(MessagingException exception) {
            LOGGER.error(exception.getLocalizedMessage());
        } mailSender.send(mail);
    }
}