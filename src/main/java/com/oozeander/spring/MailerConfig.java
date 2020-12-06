package com.oozeander.spring;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ComponentScan(basePackages = {"com.oozeander.service"})
@PropertySource("classpath:mailer.properties")
public class MailerConfig {
    @Autowired
    private ConfigurableEnvironment env;

    @Bean JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getRequiredProperty("mailer.host"));
        mailSender.setPort(Integer.parseInt(env.getRequiredProperty("mailer.port")));
        mailSender.setUsername(env.getRequiredProperty("mailer.user"));
        mailSender.setPassword(env.getRequiredProperty("mailer.pass"));
        mailSender.setJavaMailProperties(javaMailProperties());
        return mailSender;
    }

    private Properties javaMailProperties() {
        Properties javaMailProperties = new Properties();
        javaMailProperties.putAll(Map.of(
            "mail.transport.protocol", env.getRequiredProperty("mailer.protocol"),
            "mail.smtp.auth", env.getRequiredProperty("mailer.smtp.auth"),
            "mail.smtp.starttls.enable", env.getRequiredProperty("mailer.smtp.starttls"),
            "mail.debug", env.getRequiredProperty("mailer.debug")
        ));
        return javaMailProperties;
    }
}