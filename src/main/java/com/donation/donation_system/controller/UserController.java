package com.donation.donation_system.controller;

import com.donation.donation_system.model.Donation;
import com.donation.donation_system.model.User;
import com.donation.donation_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import static com.donation.donation_system.api.StringAPI.encodePassword;
import static com.donation.donation_system.utils.Constants.TOTAL_ITEMS_PER_PAGE;

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
        if (user == null) {
            return "login";
        }
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
        if (user == null) {
            return "login";
        }
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
        String error = "";
        if (!user.getPassword().equals(encodePassword(oldpassword))) {
            error = "old password is incorrect!";
            model.addAttribute("error", error);
            return "user/changepassword";
        }
        if (!newpassword.equals(confirmpassword)) {
            error = "confirm password is incorrect!";
            model.addAttribute("error", error);
            return "user/changepassword";
        }
        if (newpassword.equals(oldpassword)) {
            error = "newpassword is the same!";
            model.addAttribute("error", error);
            return "user/changepassword";
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

    @GetMapping("/donationhistory")
    public String donationhistory(Model model, HttpSession session) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        User user = (User) session.getAttribute("user");
        int page = 1;
        if (user == null) {
            return "login";
        }

        List<Donation> donationList = userService.getPageDonationListByUser(page, user.getId());
        System.out.println("Donation List:" + donationList);
        System.out.println("Donation List by user :" + donationList.size());
        int totalItems = userService.getTotalDonationByUser(user.getId());
        int totalPages = (int) Math.ceil((double) totalItems / TOTAL_ITEMS_PER_PAGE);

        model.addAttribute("donationList", donationList);
        model.addAttribute("totalPages", totalPages);
        return "user/donationhistory";
    }

    @PostMapping("admin/user")
    @ResponseBody
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("admin/user/update/{id}")
    @ResponseBody
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("admin/user/update//{id}")
    @ResponseBody
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PutMapping("admin/user/lock/{id}")
    @ResponseBody
    public User lockOrUnlockUser(@PathVariable int id, @RequestParam String status) {
        return userService.lockOrUnlockUser(id, status);
    }

    @GetMapping("admin/user")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("admin/user/search")
    @ResponseBody
    public User findByUsername(@RequestParam String username) {
        return userService.findByUsername(username);
    }
}
