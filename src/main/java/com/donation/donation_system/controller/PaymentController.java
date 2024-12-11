package com.donation.donation_system.controller;

import com.donation.donation_system.model.Donation;
import com.donation.donation_system.model.Fund;
import com.donation.donation_system.model.User;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.FundService;
import com.donation.donation_system.service.UserService;
import com.donation.donation_system.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/Donations")
public class PaymentController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private FundService fundService;

    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @PostMapping("/pay")
    public String createPayment(Model model,
                                HttpServletRequest request,
                                HttpSession session,
                                @RequestParam("fund-id") String fund_id,
                                @RequestParam("donation-amount") String donation_amount,
                                @RequestParam(value = "donation-message", defaultValue = "") String donation_message) {
        System.out.println("fund_id: " + fund_id);
        System.out.println("donation_amount: " + donation_amount);
        System.out.println("donation_message: " + donation_message);
        session.setAttribute("fund_id", fund_id);
        session.setAttribute("donation_message", donation_message);

//        Donation donation = new Donation();
//        donation.setDonationAmount(Integer.parseInt(donation_amount));
//        donation.setDonationMessage(donation_message);
//        donation.setCreatedDate();

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        System.out.println("baseUrl: " + baseUrl);
        String vnpayUrl = vnPayService.createOrder(Integer.parseInt(donation_amount), donation_message, baseUrl);
        System.out.println("vnpayUrl: " + vnpayUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String callbackPayment(
            Model model,
            HttpSession session,
            HttpServletRequest request,
            @RequestParam(value = "vnp_Amount") String vnp_Amount,
            @RequestParam(value = "vnp_PayDate") String vnp_PayDate
    ) throws ParseException, SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        int paymentStatus = vnPayService.orderReturn(request);
        User user = (User) session.getAttribute("user");
        String fundId = (String) session.getAttribute("fund_id");
        String donationMessage = (String) session.getAttribute("donation_message");
        SimpleDateFormat urlDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date payDate = urlDateFormat.parse(vnp_PayDate);

        SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String standardPayDate = standardDateFormat.format(payDate);

        Optional<Fund> fundOptional = fundService.findById(Integer.parseInt(fundId));
        Fund fund = fundOptional.get();

        User userName = userService.findUserById(user.getId());

        model.addAttribute("fund_id", fundId);
        model.addAttribute("fund_name", fund.getName());
        model.addAttribute("donation_amount", vnp_Amount);
        model.addAttribute("donation_message", donationMessage);
        model.addAttribute("userID", user.getId());
        model.addAttribute("userName", userName.getFullName());
        model.addAttribute("vnp_PayDate", standardPayDate);

        Donation donation = new Donation();
        donation.setDonationAmount(Integer.parseInt(vnp_Amount));
        donation.setDonationMessage(donationMessage);
        donation.setCreatedDate(java.sql.Date.valueOf(standardPayDate));
        donation.setFund(fund);
        donation.setUser(user);

        Donation result = donationService.save(donation);

        if (result == null) {
            return paymentStatus == 1 ? "user/paymentsuccess" : "user/paymentfail";
        } else {
            return paymentStatus == 0 ? "user/paymentfail" : "user/paymentsuccess";
        }
    }
}
