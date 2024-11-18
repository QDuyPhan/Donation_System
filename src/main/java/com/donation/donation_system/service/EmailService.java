package com.donation.donation_system.service;

import com.donation.donation_system.model.User;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendMailTest();

    void sendMailRegisterUser(User user, String password);
}
