package com.donation.donation_system.controller;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Fund;
import com.donation.donation_system.service.CategoryService;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Controller
@RequestMapping("/admin")
public class ACategoryController {
    @Autowired
    private CategoryService categoryService; // Gọi Service xử lý logic liên quan đến Category

    @GetMapping("/category")
    public String showCategories(Model model) {
        // Truy xuất danh sách danh mục từ CSDL
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category";
    }
}
