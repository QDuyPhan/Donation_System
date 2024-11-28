package com.donation.donation_system.controller;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Fund;
import com.donation.donation_system.service.CategoryService;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.FundService;
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
import java.util.*;

import static com.donation.donation_system.utils.Constants.TOTAL_ITEMS_PER_PAGE;

@Controller
@RequestMapping("/Donations")
public class CategoryController {
    @Autowired
    private FundService fundService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/category")
    public String showCategory(@RequestParam(name = "id", required = false, defaultValue = "") int id, Model model) {

        List<Fund> fundList = fundService.getByCategoryId(id);
        Optional<Category> categoryOptional = categoryService.FindById(id);
        List<Category> categories = categoryService.FindAll();
        Map<Integer, Integer> totalDonations = new HashMap<>();
        Map<Integer, Integer> sumDonations = new HashMap<>();
        for (Fund fund : fundList) {

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
        model.addAttribute("fundList", fundList);
        if (categoryOptional.isPresent()) {
            model.addAttribute("category", categoryOptional.get());
        } else {
            model.addAttribute("category", null);
        }
        model.addAttribute("totalDonations", totalDonations);
        model.addAttribute("sumDonations", sumDonations);
        model.addAttribute("content", "/pages/SearchCategory");
        return "index";
    }

    @GetMapping("/admin/category")
    public String showCategoryAdmin(Model model, HttpSession session,
                                    @RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "") String id,
                                    @RequestParam(required = false, defaultValue = "") String name,
                                    @RequestParam(required = false, value = "action", defaultValue = "") String action
    )
            throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        if (id == null) id = "";
        if (name == null) name = "";
        Pageable pageable = PageRequest.of(page, TOTAL_ITEMS_PER_PAGE);
        Page<Category> categoryList = categoryService.getPage(id, name, pageable);
//        option
//        int totalItems = categoryService.getTotalItems(id, name);
//        int totalPages = (int) Math.ceil((double) totalItems / TOTAL_ITEMS_PER_PAGE);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("size", categoryList.getSize());
        model.addAttribute("totalPages", categoryList.getTotalPages());
        model.addAttribute("totalElements", categoryList.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "admin/category/category";
    }

    @GetMapping("/admin/category/add")
    public String showCategoryAdminAddForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        List<String> statusList = Arrays.asList("Enable", "Disable");
        model.addAttribute("statusList", statusList);
        return "admin/category/category-form-add";
    }

    @PostMapping("/admin/category/add")
    public String processCategory(@ModelAttribute("category") Category category) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        String error = "Helloo";
        String message = "";
        System.out.println("Name: " + category.getName());
        System.out.println("Description: " + category.getDescription());
        System.out.println("Status: " + category.getStatus());
//        if (category.getName().isEmpty()) {
//            error = "Category name cannot be empty";
//            return "redirect:/admin/category/add?error=" + error;
//        }
//        if (category.getDescription().isEmpty()) {
//            error = "Category description cannot be empty";
//            return "redirect:/admin/category/add?error=" + error;
//        }
//        Category result = categoryService.save(category);
//        if (result != null) {
//            message = "Category added successfully";
//            return "redirect:/admin/category/add?message=" + message;
//        } else {
//            message = "Category added failed";
//            return "redirect:/admin/category/add?message=" + message;
//        }
        return "redirect:/admin/category/add";
    }
}
