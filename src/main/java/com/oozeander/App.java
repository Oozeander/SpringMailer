package com.oozeander;

import java.io.File;

import com.oozeander.service.MailerService;
import com.oozeander.spring.JavaConfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class App {
    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(JavaConfig.class);
            // new ClassPathXmlApplicationContext("applicationContext-mailer.xml");
        ctx.registerShutdownHook();

        MailerService mailerService = ctx.getBean(MailerService.class);
        mailerService.sendMail("billel.ketrouci@gmail.com", "Spring SimpleMailMessage", "Just a test");
        mailerService.sendMail("billel.ketrouci@gmail.com", "Spring MimeMessage", "<h3 style='color: green; text-align: center;'>Just a test</h3>", 
            new File[] {new File("C:\\Users\\bille\\Pictures\\meuf.jpg"), new File("C:\\Users\\bille\\Documents\\JEE-Basics\\applicationContext-mailer.xml")});

        ctx.close();
    }   
}