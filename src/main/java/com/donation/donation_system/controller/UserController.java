package com.donation.donation_system.controller;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Donation;
import com.donation.donation_system.model.Fund;
import com.donation.donation_system.model.User;
import com.donation.donation_system.service.CategoryService;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.FundService;
import com.donation.donation_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.donation.donation_system.api.StringAPI.encodePassword;
import static utils.Constants.TOTAL_ITEMS_PER_PAGE;

@Controller
@RequestMapping("/Donations")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FundService fundService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private CategoryService categoryService;

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
    public String donationhistory(Model model, HttpSession session,
                                  @RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "") String id
    ) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        User user = (User) session.getAttribute("user");
        Pageable pageable = PageRequest.of(page, TOTAL_ITEMS_PER_PAGE);
        if (user == null) {
            model.addAttribute("content", "/pages/home");
            List<Fund> funds = fundService.FindAll();
            List<Category> categories = categoryService.FindAll();
            Map<Integer, Integer> totalDonations = new HashMap<>();
            Map<Integer, Integer> sumDonations = new HashMap<>();
            model.addAttribute("categories", categories);
            model.addAttribute("funds", funds);
            model.addAttribute("totalDonations", totalDonations);
            model.addAttribute("sumDonations", sumDonations);
            return "index";
        }
        if (id == null) id = "";
        Page<Donation> donationList = donationService.getPage(user.getId(), pageable);
        System.out.println("donationList: " + donationList);
        System.out.println("size: " + donationList.getSize());
        System.out.println("totalPages: " + donationList.getTotalPages());
        System.out.println("totalElements: " + donationList.getTotalElements());
        System.out.println("currentPage: " + page);

        model.addAttribute("donationList", donationList);
        model.addAttribute("size", donationList.getSize());
        model.addAttribute("totalPages", donationList.getTotalPages());
        model.addAttribute("totalElements", donationList.getTotalElements());
        model.addAttribute("currentPage", page);


        return "user/donationhistory";
    }
}
