package com.donation.donation_system.controller;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Fund;
import com.donation.donation_system.model.User;
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

import static utils.Constants.TOTAL_ITEMS_PER_PAGE;


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
                                    @RequestParam(required = false, defaultValue = "") String name
    )
            throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        if (id == null) id = "";
        if (name == null) name = "";
        Pageable pageable = PageRequest.of(page, TOTAL_ITEMS_PER_PAGE);
        Page<Category> categoryList = categoryService.getPage(id, name, pageable);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("size", categoryList.getSize());
        model.addAttribute("totalPages", categoryList.getTotalPages());
        model.addAttribute("totalElements", categoryList.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "admin/category/category";
    }

    @GetMapping("/admin/category/addCategory")
    public String addCategory(Model model) {
        List<String> statusList = Arrays.asList("Enable", "Disable");
        model.addAttribute("category", new Category());
        model.addAttribute("statusList", statusList);
        return "admin/category/addCategory";
    }

    @PostMapping("/admin/category/addCategory")
    public String addCategory(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("status") String status) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {

        String error = "";
        String message = "";
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setStatus(status);

        System.out.println("name: " + name);
        System.out.println("description: " + description);
        System.out.println("status: " + status);

        Category newCategory = categoryService.save(category);
        if (newCategory != null) {
            message = "Category added successfully";
        } else {
            error = "Category could not be added";
        }
        model.addAttribute("message", message);
        model.addAttribute("error", error);
        return "admin/category/addCategory";
    }

    @GetMapping("/admin/category/updateCategory")
    public String updateCategory(Model model, @RequestParam("categoryId") Integer id) {
        List<String> statusList = Arrays.asList("Enable", "Disable");
        Optional<Category> categoryOptional = categoryService.FindById(id);
        model.addAttribute("category", categoryOptional.get());
        model.addAttribute("statusList", statusList);
        return "admin/category/updateCategory";
    }

    @PostMapping("/admin/category/updateCategory")
    public String updateCategory(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("status") String status) {
        String error = "";
        String message = "";
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setStatus(status);
        boolean result = categoryService.update(category);
        if (result) {
            message = "Category update successfully!";
        } else {
            error = "Category update failed!";
        }
        return "admin/category/category";
    }
}
