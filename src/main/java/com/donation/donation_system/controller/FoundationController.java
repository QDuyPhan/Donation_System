package com.donation.donation_system.controller;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Fund;
import com.donation.donation_system.service.CategoryService;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.FoundationService;
import com.donation.donation_system.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Donations")
public class FoundationController {

    private final FoundationService foundationService;
    private final CategoryService categoryService;
    @Autowired
    public FoundationController(FoundationService foundationService, CategoryService categoryService) {
        this.foundationService = foundationService;

        this.categoryService = categoryService;
    }

    @GetMapping("/foundation")
    public String FounDonaionController(Model model) {
        List<Category> categories = categoryService.FindAll();
        model.addAttribute("categories", categories);
        model.addAttribute("foundation", foundationService.findAll());
        model.addAttribute("content", "/pages/FounDonation");
        return "index";
    }
}
