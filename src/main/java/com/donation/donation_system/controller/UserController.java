package com.donation.donation_system.controller;

import com.donation.donation_system.model.User;
import com.donation.donation_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static com.donation.donation_system.api.StringAPI.encodePassword;

@Controller
@RequestMapping("/Donations")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/forgotpassword")
    public String forgotpassword() {
        return "forgotpassword";
    }


    @GetMapping("/user-info")
    public String userinfo(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "user/userinfo";
    }

    @PostMapping("/user-info")
    public String processUpdateUserInfo(@ModelAttribute("user") User user, HttpSession session, Model model) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        String username = user.getUsername();
        String fullName = user.getFullName();
        String email = user.getEmail();
        String sdt = user.getSdt();
        String diachi = user.getDiachi();
        boolean result = userService.updateUserInfo(username, fullName, email, sdt, diachi);
        String message = "";
        if (result) {
            message = "Cập nhật thành công";
        } else {
            message = "Cập nhật không thành công";
        }
        model.addAttribute("message", message);
        return "user/userinfo";
    }

    @GetMapping("/password")
    public String changepassword(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "user/changepassword";
    }

    @PostMapping("/password")
    public String processChangePassword(@ModelAttribute("user") User user,
                                        @RequestParam("username") String username,
                                        @RequestParam("oldpassword") String oldpassword,
                                        @RequestParam("newpassword") String newpassword,
                                        @RequestParam("confirmpassword") String confirmpassword,
                                        Model model, HttpSession session) throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
        user = (User) session.getAttribute("user");
        String message = "";
        if (!user.getPassword().equals(encodePassword(oldpassword))) {
            message = "old password is incorrect!";
            return "redirect:/user/changepassword?error=" + message;
        }
        if (!newpassword.equals(confirmpassword)) {
            message = "confirm password is incorrect!";
            return "redirect:/user/changepassword?error=" + message;
        }
        if (newpassword.equals(oldpassword)) {
            message = "newpassword is the same!";
            return "redirect:/user/changepassword?error=" + message;
        }
        boolean result = userService.updatePassword(newpassword, username);
        if (result) {
            message = "Update new password success!";
        } else {
            message = "Update new password failed!";
        }
        model.addAttribute("message", message);
        return "user/changepassword";
    }
}
