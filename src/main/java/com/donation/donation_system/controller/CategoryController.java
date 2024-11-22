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
@RequestMapping("/Donations")
public class CategoryController {

    private final FundService fundService;
    private final DonationService donationService;
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(FundService fundService,DonationService donationService,CategoryService categoryService) {
        this.fundService = fundService;
        this.donationService = donationService;
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String showCategory(@RequestParam(name = "id", required = false, defaultValue = "")  int id, Model model) {

        List<Fund> fundList = fundService.getByCategoryId(id);
        Optional<Category> categoryOptional = categoryService.FindById(id);
        List<Category> categories = categoryService.FindAll();
        Map<Integer, Integer> totalDonations = new HashMap<>();
        Map<Integer, Integer> sumDonations = new HashMap<>();
        for (Fund fund : fundList) {

            Integer  total = donationService.findTotalDonationsByFund(fund.getId());
            Integer  sumdonation = donationService.countDonationsByFund(fund.getId());
            System.out.println("Total donations for fund ID " + fund.getId() + ": " + total);
            totalDonations.put(fund.getId(), total);
            sumDonations.put(fund.getId(),sumdonation);
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
}
