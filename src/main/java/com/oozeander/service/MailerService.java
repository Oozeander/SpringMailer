package com.oozeander.service;

import java.io.File;

public interface MailerService {
    void sendMail(String to, String subject, String text);
    void sendMail(String to, String subject, String text, File[] files);
}