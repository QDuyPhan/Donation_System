package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.User;
import com.donation.donation_system.service.EmailService;
import com.donation.donation_system.service.ThymeleafService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static utils.Constants.*;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ThymeleafService thymeleafService;

    @Value("${spring.mail.username}")
    private String email;


    @Override
    public void sendMailTest() {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setFrom(email);
            helper.setText(thymeleafService.createContent("mail-sender-test.html", null), true);
            helper.setCc("springbootselfcode@gmail.com");
            helper.setTo("kayteecmc@gmail.com");
            helper.setSubject("Mail Test With Template HTML");

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailRegisterUser(User user, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            helper.setTo(user.getEmail());
            helper.setSubject("THÔNG TIN XÁC NHẬN ĐĂNG KÝ TÀI KHOẢN");
            Map<String, Object> variables = new HashMap<>();
            variables.put("username", user.getUsername());
            variables.put("fullName", user.getFullName());
            variables.put("sdt", user.getSdt());
            variables.put("email", user.getEmail());
            variables.put("diachi", user.getDiachi());
            variables.put("id", user.getId());
            variables.put("activate", STATUS_ACTIVE);
            variables.put("password", password);
            helper.setText(thymeleafService.createContent("register-email.html", variables), true);
            helper.setFrom(MY_EMAIL);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
