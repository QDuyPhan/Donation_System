package com.donation.donation_system.service;

import com.donation.donation_system.model.Donation;
import com.donation.donation_system.model.User;
import com.donation.donation_system.service.implement.UserNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
public interface UserService {
    User findByUsername(String name);

    User findUserById(int id) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException;

    User findByEmail(String email);

    User save(User user);

    boolean activate(String username, String id);

    boolean updateStatusAfterActivated(int id);

    boolean check(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

    HashMap<String, Object> validate(String username, String password)
            throws SQLException, ClassNotFoundException, NoSuchAlgorithmException;

    boolean updateUserInfo(String username, String fullname, String email, String sdt, String diachi) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

    boolean updatePassword(String password, String username) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

    List<Donation> getPageDonationListByUser(int page, int userId) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

    int getTotalDonationByUser(int id) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

    void updateResetPasswordToken(String token, String email) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UserNotFoundException;

    String getSiteURL(HttpServletRequest request) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UserNotFoundException;

    void sendEmail(String email, String resetPasswordLink) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException, UserNotFoundException;

    User get(String resetPasswordToken);

    void updatePassword(User user, String newPassword) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
}
