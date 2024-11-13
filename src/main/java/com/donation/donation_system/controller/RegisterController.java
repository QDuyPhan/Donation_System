package com.donation.donation_system.controller;

import com.donation.donation_system.model.User;
import com.donation.donation_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

//import static com.donation.donation_system.api.StringAPI.encodePassword;
import static com.donation.donation_system.api.StringAPI.*;
import static utils.Constants.STATUS_NOTACTIVATED;
import static utils.Constants.USER_ROLE;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";  // Trả về trang index.html
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") User user, Model model) {
        try {
            String password = generateStrongPassword(8);
            String hash = encodePassword(password);
            user.setPassword((hash));
            user.setStatus(STATUS_NOTACTIVATED);
            user.setRole(USER_ROLE);
            System.out.println(user.getPassword());

            User existUser = userService.findByUsername(user.getUsername());
            if (existUser != null) {
                System.out.println("Exist username " + existUser);
                return "redirect:/register?exisuser=Username is already exist!";
            }
            existUser = userService.findByEmail(user.getEmail());
            if (existUser != null) {
                System.out.println("Exist email " + existUser);
                return "redirect:/register?exisemail=Email is already exist!";
            }
            User returnUser = userService.save(user);
            if (returnUser != null) {
                userService.sendMail(returnUser, returnUser.getEmail(), password);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "register";
    }

}
