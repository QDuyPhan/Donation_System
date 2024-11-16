package com.donation.donation_system.controller;

import com.donation.donation_system.model.User;
import com.donation.donation_system.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;

@Controller
@RequestMapping("/Donations")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ckUser")) {
                    model.addAttribute("username", cookie.getValue());
                } else if (cookie.getName().equals("ckPassword")) {
                    model.addAttribute("password", cookie.getValue());
                }
            }
        }
        return "login";
    }

    @GetMapping("/admin/home")
    public String admin(Model model) {
        return "admin/home";
    }

    @PostMapping("/login")
    public String progressLogin(@RequestParam(value = "rememberme", required = false) String remember, @ModelAttribute("user") User user, Model model, HttpSession session, HttpServletResponse response) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        model.addAttribute("user", new User());
        String username = user.getUsername();
        String password = user.getPassword();
        Cookie ckUser = new Cookie("ckUser", username);
        Cookie ckPassword = new Cookie("ckPassword", password);
        HashMap<String, Object> validateResults = userService.validate(username, password);
        boolean isValidate = (boolean) validateResults.get("isValidate");
        if (!isValidate) {
            String message = (String) validateResults.get("message");
            model.addAttribute("message", message);
            return "redirect:/Donations/login";
        } else {
            user = (User) validateResults.get("user");
            session.setAttribute("user", user);
            if (remember != null) {
                System.out.println("cookie");
                System.out.println(ckUser);
                System.out.println(ckPassword);
                ckUser = new Cookie("ckUser", username);
                ckUser.setMaxAge(5000);
                ckPassword = new Cookie("ckPassword", password);
                response.addCookie(ckUser);
                response.addCookie(ckPassword);
            } else {
                System.out.println("nocookie");
                ckUser = new Cookie("ckUser", "");
                ckUser.setMaxAge(0);
                response.addCookie(ckUser);
                ckPassword = new Cookie("ckPassword", "");
                ckPassword.setMaxAge(0);
                response.addCookie(ckPassword);
            }
        }

        if (user.getRole() == 1) {
            return "Donations/admin/home";
        } else {
            return "Donations/home";
        }
    }
}
