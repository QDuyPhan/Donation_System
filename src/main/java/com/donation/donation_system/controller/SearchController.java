package com.donation.donation_system.controller;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Fund;
import com.donation.donation_system.service.CategoryService;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.FundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Donations")
public class SearchController {

    private final FundService fundService;
    private final DonationService donationService;
    private final CategoryService categoryService;
    @Autowired
    public SearchController(FundService fundService,DonationService donationService,CategoryService categoryService) {
        this.fundService = fundService;
        this.donationService = donationService;
        this.categoryService = categoryService;
    }


    @GetMapping("/search")
    public String search(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword, Model model) {
        model.addAttribute("content", "/pages/SearchResult");
        model.addAttribute("keyword", keyword);
        List<Category> categories = categoryService.FindAll();
        Map<Integer, Integer> totalDonations = new HashMap<>();
        Map<Integer, Integer> sumDonations = new HashMap<>();
        // Thực hiện logic tìm kiếm (gọi service, query database)
        List<Fund> fundList = fundService.getAllBySearch(keyword);

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
        model.addAttribute("sumDonations", sumDonations);
        model.addAttribute("totalDonations", totalDonations);
        model.addAttribute("fundList", fundList);


        return "index";
    }

    @GetMapping("/searchTest")
    @ResponseBody
    public List<Fund> getAllBySearch(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        return fundService.getAllBySearch(keyword);
    }
}


