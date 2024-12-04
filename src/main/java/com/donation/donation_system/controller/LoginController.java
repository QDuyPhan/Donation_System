package com.donation.donation_system.controller;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Fund;
import com.donation.donation_system.model.User;
import com.donation.donation_system.service.CategoryService;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.FundService;
import com.donation.donation_system.service.UserService;
import com.donation.donation_system.service.implement.UserNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import net.bytebuddy.utility.RandomString;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Donations")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private FundService fundService;

    @Autowired
    private DonationService donationService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ckUser")) {
                    model.addAttribute("username", cookie.getValue());
                } else if (cookie.getName().equals("ckPassword")) {
                    model.addAttribute("password", cookie.getValue());
                }
            }
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    @PostMapping("/login")
    public String progressLogin(@RequestParam(value = "rememberme", required = false) String remember,
                                @ModelAttribute("user") User user, Model model, HttpSession session,
                                HttpServletResponse response
    ) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        model.addAttribute("user", new User());
        String username = user.getUsername();
        String password = user.getPassword();
        Cookie ckUser = new Cookie("ckUser", username);
        Cookie ckPassword = new Cookie("ckPassword", password);
        HashMap<String, Object> validateResults = userService.validate(username, password);
        user = (User) validateResults.get("user");
        session.setAttribute("user", user);
        boolean isValidate = (boolean) validateResults.get("isValidate");
        if (!isValidate) {
            String message = (String) validateResults.get("message");
            model.addAttribute("message", message);
            return "login";
        } else {
            System.out.println("Login user " + user);
            if (remember != null && remember.equals("on")) {
                System.out.println("cookie");
                System.out.println(ckUser);
                System.out.println(ckPassword);
                ckUser = new Cookie("ckUser", username);
                ckPassword = new Cookie("ckPassword", password);
                ckUser.setMaxAge(60 * 60 * 24);
                ckPassword.setMaxAge(60 * 60 * 24);
                response.addCookie(ckUser);
                response.addCookie(ckPassword);
            } else {
                System.out.println("nocookie");
                ckUser = new Cookie("ckUser", "");
                ckPassword = new Cookie("ckPassword", "");
                ckUser.setMaxAge(0);
                ckPassword.setMaxAge(0);
                response.addCookie(ckUser);
                response.addCookie(ckPassword);
            }
        }
        if (user.getRole() == 1) {
            model.addAttribute("contentAdmin", "/admin/home/home");
            return "admin/home/index";
        } else {
            model.addAttribute("content", "/pages/home");
            List<Fund> funds = fundService.FindAll();
            List<Category> categories = categoryService.FindAll();
            Map<Integer, Integer> totalDonations = new HashMap<>();
            Map<Integer, Integer> sumDonations = new HashMap<>();
            for (Fund fund : funds) {

                Integer total = donationService.findTotalDonationsByFund(fund.getId());
                Integer sumdonation = donationService.countDonationsByFund(fund.getId());
                System.out.println("Total donations for fund ID " + fund.getId() + ": " + total);
                totalDonations.put(fund.getId(), total);
                sumDonations.put(fund.getId(), sumdonation);
                double percentAchieved = 0;
                if (totalDonations.containsKey(fund.getId()) && fund.getExpectedResult() > 0) {
                    percentAchieved = (int) (100.0 * totalDonations.get(fund.getId()) / fund.getExpectedResult());
                }
                fund.setPercentAchieved(percentAchieved);
            }
            model.addAttribute("categories", categories);
            model.addAttribute("funds", funds);
            model.addAttribute("totalDonations", totalDonations);
            model.addAttribute("sumDonations", sumDonations);
            return "index";
        }
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model, HttpSession session) {
        model.addAttribute("pageTitle", "Forgot Password");

        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPasswordForm(HttpServletRequest request, Model model) {
        String email = request.getParameter("Email");
        String token = RandomString.make(45);

        try {
            userService.updateResetPasswordToken(token, email);

            String resetPasswordLink = userService.getSiteURL(request) +
                    "/Donations/resetPassword?token=" + token;
            userService.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "we have sent a reset password link to your email.");
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error while sending email.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("pageTitle", "Forgot Password");
        return "forgotPassword";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm(@Param("token") String token, Model model) {
        User user = userService.get(token);
        if (user == null) {
            model.addAttribute("error", "Invalid token");
            return "error";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset your Password");
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String processResetPasswordForm(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("newPassword");
        System.out.println("token: " + token);
        System.out.println("Password: " + password);
        User user = userService.get(token);
        if (user == null) {
            model.addAttribute("error", "Invalid token");
            return "resetPassword";
//            return "error";
//            return "redirect:/resetPassword?error=Invalid token";
        } else {
            try {
                userService.updatePassword(user, password);
            } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            model.addAttribute("message", "Change password successfully");
            return "resetPassword";
//            return "redirect:/resetPassword?message=Change password successfully";
        }
//        return "message";
    }
}
