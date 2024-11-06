package com.donation.donation_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/")
    public String homepage() {
        return "index";  // Trả về trang index.html
    }
}
