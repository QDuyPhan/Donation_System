package com.donation.donation_system.controller;

import com.donation.donation_system.model.*;
import com.donation.donation_system.service.CategoryService;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.FoundationService;
import com.donation.donation_system.service.FundService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static utils.Constants.TOTAL_ITEMS_PER_PAGE;

@Controller
@RequestMapping("/Donations")
public class FundController {

    @Autowired
    private FundService fundService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FoundationService foundationService;


    // Lấy tất cả các quỹ
    @GetMapping
    public List<Fund> getAllFunds() {
        return fundService.FindAll();
    }

    // Lấy quỹ theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Fund>> getFundById(@PathVariable int id) {
        Optional<Fund> fund = fundService.findById(id);
        if (fund.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Trả về 404 nếu không tìm thấy quỹ
        }
        return ResponseEntity.ok(fund);
    }

    // Tạo mới một quỹ
    @PostMapping
    public ResponseEntity<Fund> createFund(@RequestBody Fund fund) {
        Fund createdFund = fundService.save(fund);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFund);  // Trả về 201 khi tạo thành công
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fund> updateFund(@PathVariable int id, @RequestBody Fund fundDetails) {
        // Tìm quỹ theo ID
        Optional<Fund> fundOptional = fundService.findById(id);
        if (fundOptional.isEmpty()) {
            // Trả về mã lỗi 404 nếu không tìm thấy quỹ
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Lấy đối tượng fund từ Optional
        Fund fund = fundOptional.get();

        // Cập nhật các thông tin của quỹ
        if (fundDetails.getName() != null) {
            fund.setName(fundDetails.getName());
        }
        if (fundDetails.getDescription() != null) {
            fund.setDescription(fundDetails.getDescription());
        }
        if (fundDetails.getContent() != null) {
            fund.setContent(fundDetails.getContent());
        }
        if (fundDetails.getImageUrl() != null) {
            fund.setImageUrl(fundDetails.getImageUrl());
        }
        if (fundDetails.getExpectedResult() != null && fundDetails.getExpectedResult() != 0) {
            fund.setExpectedResult(fundDetails.getExpectedResult());
        }
        if (fundDetails.getStatus() != null) {
            fund.setStatus(fundDetails.getStatus());
        }
        if (fundDetails.getEndDate() != null) {
            fund.setEndDate(fundDetails.getEndDate());
        }
        if (fundDetails.getCategory() != null) {
            fund.setCategory(fundDetails.getCategory());
        }
        if (fundDetails.getFoundation() != null) {
            fund.setFoundation(fundDetails.getFoundation());
        }

        // Lưu lại quỹ đã được cập nhật
        Fund updatedFund = fundService.save(fund);

        // Trả về quỹ đã cập nhật với mã trạng thái 200 OK
        return ResponseEntity.ok(updatedFund);
    }


    // Xóa một quỹ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFund(@PathVariable Integer id) {
        try {
            fundService.deleteById(id);
            return ResponseEntity.noContent().build();  // Trả về 204 khi xóa thành công
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Trả về 404 nếu không tìm thấy quỹ
        }
    }

    @GetMapping("/detailFund")
    public String detailFund(@RequestParam(name = "id", required = false, defaultValue = "") int id, Model model, HttpSession session) {
        Fund fund = fundService.getFundById(id);
        List<Donation> donationFund = donationService.findDonationById(id);
        List<Category> categories = categoryService.FindAll();
        Map<Integer, Integer> totalDonations = new HashMap<>();
        Map<Integer, Integer> sumDonations = new HashMap<>();
        User user = (User) session.getAttribute("user");

        Integer total = donationService.findTotalDonationsByFund(fund.getId());
        Integer sumdonation = donationService.countDonationsByFund(fund.getId());
        totalDonations.put(fund.getId(), total);
        sumDonations.put(fund.getId(), sumdonation);
        double percentAchieved = 0;
        if (totalDonations.containsKey(fund.getId()) && fund.getExpectedResult() > 0) {
            percentAchieved = (int) (100.0 * totalDonations.get(fund.getId()) / fund.getExpectedResult());
        }
        List<Donation> limitedDonations = donationFund.stream().limit(2).collect(Collectors.toList());
        List<Donation> allDonations = new ArrayList<>(); // Khởi tạo danh sách trống mặc định

        if (donationFund.size() > 2) {
            allDonations = donationFund.subList(2, donationFund.size());
        }

        System.out.println(limitedDonations);
        model.addAttribute("limitedDonations", limitedDonations); // Gửi mảng ít
        model.addAttribute("allDonations", allDonations); // Gửi mảng nhiều

        fund.setPercentAchieved(percentAchieved);
        model.addAttribute("categories", categories);
        model.addAttribute("donationFund", donationFund);
        model.addAttribute("fund", fund);
        model.addAttribute("totalDonations", totalDonations);
        model.addAttribute("sumDonations", sumDonations);
        model.addAttribute("content", "/component/detailProject");
        return "index";
    }

    @GetMapping("/admin/fund")
    public String adminFund(Model model, HttpSession session,
                            @RequestParam(required = false, defaultValue = "0") int page,
                            @RequestParam(required = false, defaultValue = "") String id,
                            @RequestParam(required = false, defaultValue = "") String name,
                            @RequestParam(required = false, defaultValue = "") String foundation,
                            @RequestParam(name = "filter-category", required = false, defaultValue = "") String category,
                            @RequestParam(required = false, value = "action", defaultValue = "") String action) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        if (id == null) id = "";
        if (name == null) name = "";
        if (foundation == null) foundation = "";

        category = (category == null) ? "" : category;
        Pageable pageable = PageRequest.of(page, TOTAL_ITEMS_PER_PAGE);
        Page<Fund> fundList = fundService.getPage(id, name, foundation, category, pageable);
        List<Category> categoryList = categoryService.search("");


        model.addAttribute("fundList", fundList);
        model.addAttribute("size", fundList.getSize());
        model.addAttribute("totalPages", fundList.getTotalPages());
        model.addAttribute("totalElements", fundList.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("foundation", foundation);
        model.addAttribute("category", category);
        model.addAttribute("categoryList", categoryList);
        return "admin/fund/fund";
    }

    @GetMapping("admin/fund/add")
    public String showFormFundAdd(Model model) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        Fund fund = new Fund();
        List<String> statusList = Arrays.asList("Opening", "Waiting", "Close", "Finish", "Disable");
//        List<Category> categoryList = categoryService.search("");
//        List<Foundation> foundationList = foundationService.search("");
        List<Category> categoryList = categoryService.findAll();
        List<Foundation> foundationList = foundationService.findAll();

        model.addAttribute("fund", fund);
        model.addAttribute("statusList", statusList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("foundationList", foundationList);
        return "admin/fund/fundAdd";
    }

//    @GetMapping("/admin/fund/add")
//    public String createFund(@ModelAttribute("fund") Fund fund, Model model, HttpSession session) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        Timestamp timestamp = Timestamp.valueOf(currentDateTime);
//
//        Fund f = new Fund();
//        f.setName(fund.getName());
//        f.setContent(fund.getContent());
//        f.setImageUrl(fund.getImageUrl());
//        f.setDescription(fund.getDescription());
//        f.setExpectedResult(fund.getExpectedResult());
//        f.setStatus(fund.getStatus());
//        f.setCreatedDate(timestamp.toLocalDateTime());
//        f.setEndDate(fund.getEndDate());
//        Foundation foundation = foundationService.findByName(fund.getName());
//        Category category = categoryService.findByName(fund.getName());
//        f.setCategory(category);
//        f.setFoundation(foundation);
//
//        Fund returnFund = fundService.save(f);
//        if (returnFund != null) {
//            return "redirect:/admin/fund?message=Add Fund Successfully";
//        } else {
//            return "redirect:/admin/fund?error=Add Fund Failed";
//        }
//    }
}
