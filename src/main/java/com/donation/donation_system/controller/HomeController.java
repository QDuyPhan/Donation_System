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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("/Donations")
public class HomeController {

    @Autowired
    private FundService fundService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        model.addAttribute("content", "/pages/home"); // Nạp fragment home
        List<Fund> funds = fundService.FindAll();

        // Tính toán percentAchieved cho từng fund
        Map<Integer, Integer> totalDonations = new HashMap<>();
        Map<Integer, Integer> sumDonations = new HashMap<>();
        for (Fund fund : funds) {
            Integer total = donationService.findTotalDonationsByFund(fund.getId());
            Integer sumdonation = donationService.countDonationsByFund(fund.getId());

            totalDonations.put(fund.getId(), total);
            sumDonations.put(fund.getId(), sumdonation);

            double percentAchieved = 0;
            if (totalDonations.containsKey(fund.getId()) && fund.getExpectedResult() > 0) {
                percentAchieved = (int) (100.0 * totalDonations.get(fund.getId()) / fund.getExpectedResult());
            }
            fund.setPercentAchieved(percentAchieved);
        }

        // Chia danh sách quỹ theo percentAchieved
        List<Fund> under100Funds = funds.stream()
                .filter(fund -> fund.getPercentAchieved() < 100)
                .toList();

        List<Fund> atLeast100Funds = funds.stream()
                .filter(fund -> fund.getPercentAchieved() >= 100)
                .toList();

        List<Donation> donationList = donationService.findTop3ByOrderByFieldAsc();
        List<Category> categories = categoryService.FindAll();

        model.addAttribute("donationList", donationList);
        model.addAttribute("categories", categories);
        model.addAttribute("funds", funds);
        model.addAttribute("under100Funds", under100Funds); // Thêm danh sách percentAchieved < 100
        model.addAttribute("atLeast100Funds", atLeast100Funds); // Thêm danh sách percentAchieved >= 100
        model.addAttribute("totalDonations", totalDonations);
        model.addAttribute("sumDonations", sumDonations);
        return "index";
    }


    @GetMapping("/test-donations")
    public ResponseEntity<List<Donation>> getDonations() {
        List<Donation> donationList = donationService.findTop3ByOrderByFieldAsc();
        return ResponseEntity.ok(donationList); // Return donationList as JSON
    }

    @GetMapping("admin/home")
    public String adminHome(Model model, HttpSession session) {
        model.addAttribute("contentAdmin", "/admin/home/home");
        return "admin/home/index";
    }

}
