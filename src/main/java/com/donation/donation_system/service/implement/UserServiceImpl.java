package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.User;
import com.donation.donation_system.repository.UserRepository;
import com.donation.donation_system.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static utils.Constants.*;

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

    @Transactional
    @Override
    public int activate(String username, String id) {
        try {
            int result = userRepository.activate(username, id);
            if (result == 1) {
                int updateResult = updateStatusAfterActivated(Integer.parseInt(id));
                if (updateResult == 1)
                    return 1;
                return 0;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Transactional
    @Override
    public int updateStatusAfterActivated(int id) {
        try {
            int resutl = userRepository.updateStatusAfterActivated(STATUS_ACTIVE, id);
            return resutl;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
