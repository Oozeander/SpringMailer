package com.oozeander.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MailerConfig.class})
public class JavaConfig {}