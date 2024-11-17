package com.donation.donation_system.controller;

import com.donation.donation_system.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/Donations")
public class HomeController {
    @GetMapping("/admin/home")
    public String admin(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println("GetMappping " + user);
        return "admin/home";
    }

    @GetMapping("/home")
    public String homeUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        return "user/home";
    }
}
