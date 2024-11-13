//package com.donation.donation_system.configuration;
//
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//@Configuration
//public class MailConfig {
//
//    @Value("${spring.mail.host}")
//    private String HOST;
//    @Value("${spring.mail.port}")
//    private Integer PORT;
//    @Value("${spring.mail.username}")
//    private String USERNAME;
//    @Value("${spring.mail.password}")
//    private String PASSWORD;
//    @Value("${spring.mail.properties.mail.transport.protocol}")
//    private String PROTOCOL;
//    @Value("${spring.mail.properties.mail.smtp.auth}")
//    private String AUTH;
//    @Value("${spring.mail.properties.mail.smtp.starttls.required}")
//    private String STARTTLS_REQUIRED;
//    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
//    private String STARTTLS_ENABLE;
//    @Value("${spring.mail.properties.mail.debug}")
//    private String DEBUG;
//
//    public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";
//
//
//    @Bean
//    JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(HOST);
//        mailSender.setPort(PORT);
//        mailSender.setUsername(USERNAME);
//        mailSender.setPassword(PASSWORD);
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", PROTOCOL);
//        props.put("mail.smtp.auth", AUTH);
//        props.put("mail.smtp.starttls.enable", STARTTLS_ENABLE);
//        props.put("mail.smtp.starttls.enable", STARTTLS_REQUIRED);
//        props.put("mail.debug", DEBUG);
//
//        return mailSender;
//    }
//
//}