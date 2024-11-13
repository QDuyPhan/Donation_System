package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.User;
import com.donation.donation_system.repository.UserRepository;
import com.donation.donation_system.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static utils.Constants.MY_EMAIL;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public User findByUsername(String name) {
        try {
            User user = userRepository.findByUsername(name);
            System.out.println("UserRepository: " + user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        try {
            User exitsEmail = userRepository.findByEmail(email);
            System.out.println("UserRepository: " + exitsEmail);
            return exitsEmail;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User save(User user) {
        try {
            User newUser = userRepository.save(user);
            return newUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendMail(User user, String email, String password) throws UnsupportedEncodingException, MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("Notifycation", MY_EMAIL);
            helper.setTo(email);
            helper.setSubject("THÔNG TIN XÁC NHẬN ĐĂNG KÝ TÀI KHOẢN");
            String templateContent = new String(Objects.requireNonNull(UserServiceImpl.class.getResourceAsStream("/templates/registeralert.html")).readAllBytes(), StandardCharsets.UTF_8);
            String content = templateContent
                    .replace("$username", user.getUsername())
                    .replace("$password", password)
                    .replace("$fullname", user.getFullName())
                    .replace("$sdt", user.getSdt())
                    .replace("$email", user.getEmail())
                    .replace("$address", user.getDiachi())
                    .replace("$key", String.valueOf(user.getId()));

            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }


}
