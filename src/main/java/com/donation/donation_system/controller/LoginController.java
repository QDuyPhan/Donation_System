package com.donation.donation_system.controller;

import com.donation.donation_system.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Donations")
public class LoginController {
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String progressLogin(@ModelAttribute("user") User user, Model model, HttpSession session) {
        model.addAttribute("user", new User());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return "login";
    }
}
