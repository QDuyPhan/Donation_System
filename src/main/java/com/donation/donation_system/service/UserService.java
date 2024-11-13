package com.donation.donation_system.service;

import com.donation.donation_system.model.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface UserService {
    User findByUsername(String name);

    User findByEmail(String email);

    User save(User user);

    void sendMail(User user, String email, String password) throws UnsupportedEncodingException, MessagingException;

}
