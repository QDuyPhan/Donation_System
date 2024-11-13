package com.donation.donation_system.controller;

import com.donation.donation_system.model.User;
import com.donation.donation_system.service.EmailService;
import com.donation.donation_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.donation.donation_system.api.StringAPI.*;
import static utils.Constants.STATUS_NOTACTIVATED;
import static utils.Constants.USER_ROLE;
//http://localhost:8080/Donations/register

@Controller
@RequestMapping("/Donations")
@RequiredArgsConstructor
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";  // Trả về trang index.html
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user, HttpSession session) {
        try {
            String password = generateStrongPassword(8);
            String hashPassword = encodePassword(password);
            user.setPassword((hashPassword));
            user.setStatus(STATUS_NOTACTIVATED);
            user.setRole(USER_ROLE);

            User existUser = userService.findByUsername(user.getUsername());
            if (existUser != null) {
                return "redirect:/register?exisuser=Username is already exist!";
            }
            existUser = userService.findByEmail(user.getEmail());
            if (existUser != null) {
                return "redirect:/register?exisemail=Email is already exist!";
            }
            User newUser = userService.save(user);
            if (newUser != null) {
                emailService.sendMailRegisterUser(newUser, password);
                session.setAttribute("newuser", newUser);
                return "redirect:/Donations";
            } else {
                System.out.println("create user fail");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "register";
    }

}
