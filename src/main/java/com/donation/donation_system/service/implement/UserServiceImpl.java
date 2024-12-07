package com.donation.donation_system.service.implement;

import com.donation.donation_system.api.StringAPI;
import com.donation.donation_system.model.Donation;
import com.donation.donation_system.model.User;
import com.donation.donation_system.repository.UserRepository;
import com.donation.donation_system.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static com.donation.donation_system.api.StringAPI.encodePassword;
import static utils.Constants.*;


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
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findByEmail(String email) {
        try {
            User exitsEmail = userRepository.findByEmail(email);
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
    @Transactional
    public boolean activate(String username, String id) {
        try {
            int result = userRepository.activate(username, id);
            if (result == 1) {
                boolean updateResult = updateStatusAfterActivated(Integer.parseInt(id));
                if (updateResult) return true;
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateStatusAfterActivated(int id) {
        try {
            int result = userRepository.updateStatusAfterActivated(STATUS_ACTIVE, id);
            if (result != 0) return true;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean check(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        int result = userRepository.check(username, StringAPI.encodePassword(password));
        if (result > 0) return true;
        return false;
    }

    @Override
    public HashMap<String, Object> validate(String username, String password) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException {
        HashMap<String, Object> bindingResults = new HashMap<>();
        boolean isExist = check(username, password);
        if (!isExist) {
            bindingResults.put("isValidate", false);
            bindingResults.put("message", "Username or password is incorrect.");
            return bindingResults;
        }
        User user = findByUsername(username);
        String userStatus = user.getStatus();
        if (userStatus.equalsIgnoreCase(STATUS_NOTACTIVATED)) {
            bindingResults.put("isValidate", false);
            bindingResults.put("message", "Account is not activated yet.");
            return bindingResults;
        } else if (userStatus.equals(STATUS_BANNED)) {
            bindingResults.put("isValidate", false);
            bindingResults.put("message", "Account is banned");
            return bindingResults;
        } else if (userStatus.equals(STATUS_INACTIVE)) {
            bindingResults.put("isValidate", false);
            bindingResults.put("message", "Account is inactive");
            return bindingResults;
        }
        bindingResults.put("isValidate", true);
        bindingResults.put("user", user);
        return bindingResults;
    }

    @Override
    @Transactional
    public boolean updateUserInfo(String username, String fullname, String email, String sdt, String diachi) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        int result = userRepository.updateUserInfo(username, fullname, email, sdt, diachi);
        if (result != 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updatePassword(String password, String username) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        int result = userRepository.updatePassword(encodePassword(password), username);
        if (result != 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Donation> getPageDonationListByUser(int page, int userId) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        int offset = (page - 1) * TOTAL_ITEMS_PER_PAGE;
        return userRepository.getPageDonationListByUser(userId, TOTAL_ITEMS_PER_PAGE, offset);
    }

    @Override
    public int getTotalDonationByUser(int id) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        int result = userRepository.getTotalDonationByUser(id);
        if (result != 0) {
            return result;
        }
        return 0;
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }
    }

    @Override
    public String getSiteURL(HttpServletRequest request) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UserNotFoundException {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @Override
    public void sendEmail(String email, String resetPasswordLink) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, MessagingException, UserNotFoundException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("resetpassword", "Reset Password");
        helper.setTo(email);
        String subject = "Link to reset password";
        String content = "<p>Click the link to change your password: </p>"
                + "<p><b><a href=\"" + resetPasswordLink + "\"> Change my password</a><b></p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public User get(String resetPasswordToken) {
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    @Override
    public void updatePassword(User user, String newPassword) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        user.setPassword(encodePassword(newPassword));
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public Page<User> getPage(String username, String phone, String email, Integer role, int page, int pageSize) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        Pageable pageable = PageRequest.of(page, pageSize);
        return userRepository.getPage(username, phone, email, role, pageable);
    }
}
