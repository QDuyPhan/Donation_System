package com.donation.donation_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("content", "/pages/home"); // Nạp fragment home
        return "index";
    }
    @GetMapping("/detailFragment")
    public String detailProject(Model model) {
        model.addAttribute("content", "/component/detailProject"); // Nạp fragment home
        return "index";
    }
}
