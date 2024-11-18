package com.donation.donation_system.controller;

import com.donation.donation_system.model.Fund;
import com.donation.donation_system.model.User;
import com.donation.donation_system.service.FundService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/Donations")
public class HomeController {

    private final FundService fundService;

    @Autowired
    public HomeController(FundService fundService) {
        this.fundService = fundService;
    }

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
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("content", "/pages/home"); // Nạp fragment home
        List<Fund> funds = fundService.FindAll();

        // Thêm danh sách quỹ vào model để truyền sang view
        model.addAttribute("funds", funds);
        return "index";
    }
    @GetMapping("/detailFragment")
    public String detailProject(Model model) {
        model.addAttribute("content", "/component/detailProject"); // Nạp fragment home
        return "index";
    }
}
