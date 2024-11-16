package com.donation.donation_system.service;

import com.donation.donation_system.model.User;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;

@Service
public interface UserService {
    User findByUsername(String name);

    User findByEmail(String email);

    User save(User user);

    int activate(String username, String id);

    int updateStatusAfterActivated(int id);

    boolean check(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

    HashMap<String, Object> validate(String username, String password)
            throws SQLException, ClassNotFoundException, NoSuchAlgorithmException;
}
