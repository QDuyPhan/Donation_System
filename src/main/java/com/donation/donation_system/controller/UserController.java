package com.donation.donation_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

        @GetMapping("/password")
        public String changePassword(@RequestParam(name = "action", required = false) String action) {
            if ("changepassword".equals(action)) {
                return "changepassword";
            }
            return "redirect:/";
        }
}
