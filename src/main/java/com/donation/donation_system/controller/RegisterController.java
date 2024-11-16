package com.donation.donation_system.controller;

import com.donation.donation_system.model.User;
import com.donation.donation_system.service.EmailService;
import com.donation.donation_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.donation.donation_system.api.StringAPI.*;
import static utils.Constants.*;
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
    public String register(Model model, HttpSession session, @RequestParam(value = "action", defaultValue = "") String action, @RequestParam(value = "username", defaultValue = "") String username, @RequestParam(value = "id", defaultValue = "") String id) {
        User user = new User();
        model.addAttribute("user", user);
        if (action != null && action.equals("activate")) {
            int result = userService.updateStatusAfterActivated(Integer.parseInt(id));
            if (result == 1) {
                return "activate-account-success";
            } else {
                return "activate-account-fail";
            }
        }
        return "register";  // Trả về trang index.html
    }

    @GetMapping("/register-success")
    public String registerSuccess() {
        return "register-success";
    }

    @GetMapping("/register-fail")
    public String registerFail() {
        return "register-fail";
    }

    @GetMapping("/activate-account-success")
    public String activateAccountSuccess() {
        return "activate-account-success";
    }

    @GetMapping("/activate-account-fail")
    public String activateAccountFail() {
        return "activate-account-fail";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user, HttpSession session, Model model) {
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
                return "register-success";
            } else {
                System.out.println("create user fail");
                return "register-fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/register";
    }

}
