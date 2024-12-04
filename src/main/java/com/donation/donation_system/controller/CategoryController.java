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

//        Optional<Category> category = categoryService.FindById(Integer.parseInt(id));
        if (action != null && action.equals("add")) {
            List<String> statusList = Arrays.asList("Enable", "Disable");
            model.addAttribute("statusList", statusList);
            model.addAttribute("action", action);
            return "admin/category/category-form";
        } else if (action != null && action.equals("edit")) {
            Optional<Category> category = categoryService.FindById(Integer.parseInt(id));
            if (category.isPresent())
                model.addAttribute("category", category.get());
            else
                model.addAttribute("category", null);
            List<String> statusList = Arrays.asList("Enable", "Disable");
            model.addAttribute("statusList", statusList);
            model.addAttribute("action", action);
            return "admin/category/category-form";
        }
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

    @PostMapping("/admin/category")
    public String processCategory(@ModelAttribute("category") Category category,
                                  @RequestParam(required = false, value = "action", defaultValue = "") String action,
                                  @RequestParam("id") String id,
                                  @RequestParam("name") String name,
                                  @RequestParam("description") String description,
                                  @RequestParam("status") String status) {
        if (action != null && action.equals("add")) {
            System.out.println("id" + id);
            System.out.println("name" + name);
            System.out.println("description" + description);
            System.out.println("status" + status);
        }
        return "admin/category/category";
    }
}
