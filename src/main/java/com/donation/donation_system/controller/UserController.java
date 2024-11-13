package com.donation.donation_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/forgotpassword")
    public String forgotpassword() {
        return "forgotpassword";  // Trả về trang index.html
    }

    @GetMapping("/changepassword")
    public String changepassword() {
        return "user/changepassword";
    }
}
