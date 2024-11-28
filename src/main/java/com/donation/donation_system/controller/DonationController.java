package com.donation.donation_system.controller;

import com.donation.donation_system.model.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import com.donation.donation_system.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static com.donation.donation_system.utils.Constants.TOTAL_ITEMS_PER_PAGE;

@Controller
@RequestMapping("Donations")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @GetMapping("/admin/donation")
    public String donation(Model model,
                           @RequestParam(required = false, defaultValue = "0") int page,
                           @RequestParam(required = false, defaultValue = "") String id,
                           @RequestParam(required = false, defaultValue = "") String username,
                           @RequestParam(required = false, defaultValue = "") String fundName
    ) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        if (id == null) id = "";
        if (username == null) username = "";
        if (fundName == null) fundName = "";
        Pageable pageable = PageRequest.of(page, TOTAL_ITEMS_PER_PAGE);
        Page<Donation> donationList = donationService.getPage(id, username, fundName, pageable);

        System.out.println("id: " + id);
        System.out.println("username: " + username);
        System.out.println("fundName: " + fundName);


        model.addAttribute("donationList", donationList);
        model.addAttribute("size", donationList.getSize());
        model.addAttribute("totalPages", donationList.getTotalPages());
        model.addAttribute("totalElements", donationList.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("id", id);
        model.addAttribute("username", username);
        model.addAttribute("fundName", fundName);
        return "admin/donations/donations";
    }
}
