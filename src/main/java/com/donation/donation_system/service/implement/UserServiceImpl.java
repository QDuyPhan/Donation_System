package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.User;
import com.donation.donation_system.repository.UserRepository;
import com.donation.donation_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

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

}
