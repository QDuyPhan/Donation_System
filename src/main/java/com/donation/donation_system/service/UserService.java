package com.donation.donation_system.service;

import com.donation.donation_system.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByUsername(String name);

    User findByEmail(String email);

    User save(User user);

    int activate(String username, String id);

    int updateStatusAfterActivated(int id);


}
