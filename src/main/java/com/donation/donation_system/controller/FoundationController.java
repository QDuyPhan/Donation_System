package com.donation.donation_system.controller;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Foundation;
import com.donation.donation_system.model.Fund;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.FoundationService;
import com.donation.donation_system.service.FundService;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

import static com.donation.donation_system.utils.Constants.TOTAL_ITEMS_PER_PAGE;

@Controller
@RequestMapping("/Donations1")
public class FoundationController {

    private final FoundationService foundationService;
    private final FundService fundService;
    private final DonationService donationService;
    private final CategoryService categoryService;

    @Autowired
    public FoundationController(FoundationService foundationService, FundService fundService, DonationService donationService,CategoryService categoryService) {
        this.foundationService = foundationService;
        this.fundService = fundService;
        this.donationService = donationService;
        this.categoryService = categoryService;
    }
    @GetMapping("/foundation")
    public String ShowFoundations(@RequestParam(name = "id",required = false,defaultValue = "")int id, Model model)
    {
        List<Fund> fundList = fundService.getByFoundationId(id);
        Optional<Foundation> foundationOptional = foundationService.findById(id);
        List<Foundation>foundations=foundationService.findAll();
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
        model.addAttribute("foundations",foundations);
        model.addAttribute("fundList",fundList);
        if(foundationOptional.isPresent()){
            model.addAttribute("foundation",foundationOptional.get());
        }
        else{
            model.addAttribute("foundation",null);
        }
        model.addAttribute("totalDonations", totalDonations);
        model.addAttribute("sumDonations", sumDonations);
        model.addAttribute("content", "/pages/SearchFoundation");
        return "index";
    }
//    public String showFoundations(Model model) {
//        List<Foundation> foundations = foundationService.findAll();
//        model.addAttribute("foundations", foundations);
//        model.addAttribute("categories", categoryService.FindAll());
//        model.addAttribute("content", "/pages/FoundationList");
//        return "index";
//    }

    // Hiển thị chi tiết Foundation
//    @GetMapping("/{id}")
//    public String showFoundationDetails(@PathVariable("id") int id, Model model) {
//        Optional<Foundation> foundationOptional = foundationService.findById(id);
//        if (foundationOptional.isPresent()) {
//            Foundation foundation = foundationOptional.get();
//            model.addAttribute("foundation", foundation);
//            model.addAttribute("funds", fundService.findById(id));  // Lấy danh sách Quỹ liên kết với Foundation
//            model.addAttribute("content", "/pages/FoundationDetail");  // Trang hiển thị chi tiết Foundation
//        } else {
//            model.addAttribute("error", "Foundation not found");
//        }
//        return "index";
//    }
    @PostMapping("/amdin/foundation")
    public String processFoundation(@ModelAttribute("foundation") Foundation foundation,
                                  @RequestParam(required = false, value = "action", defaultValue = "") String action,
                                  @RequestParam("id") String id,
                                  @RequestParam("name") String name,
                                  @RequestParam("description") String description,
                                  @RequestParam("email")String email,
                                  @RequestParam("status") String status) {
        if (action != null && action.equals("add")) {
            System.out.println("id" + id);
            System.out.println("name" + name);
            System.out.println("description" + description);
            System.out.println("email" + email);
            System.out.println("status" + status);
        }
        return "admin/foundation/foundation";
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Foundation>>GetFoundationById(@PathVariable int id)
//    {
//        Optional<Foundation> foundation = foundationService.findById(id);
//        if(foundation.isEmpty())
//        {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return ResponseEntity.ok(foundation);
//    }

    // Thêm mới Foundation
//    @GetMapping("/Foundation/Add")
//    public String showCreateFoundationForm(Model model) {
//        model.addAttribute("foundation", new Foundation());  // Tạo đối tượng Foundation mới để hiển thị trong form
//        model.addAttribute("categories", categoryService.FindAll());  // Cung cấp các category nếu cần
//        model.addAttribute("content", "/pages/FoundationCreate");  // Đường dẫn tới trang tạo mới Foundation
//        return "index";
//    }

//    @PostMapping("/foundation")
//    public String createFoundation(@ModelAttribute Foundation foundation, Model model) {
//        Foundation createdFoundation = foundationService.save(foundation);
//        model.addAttribute("foundation", createdFoundation);  // Hiển thị Foundation vừa tạo
//        return "redirect:/Donations/foundation";
//    }
    @GetMapping("/admin/foundation")
    public String showFoundationAdmin(Model model, HttpSession session,
                                    @RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "") String id,
                                    @RequestParam(required = false, defaultValue = "") String name,
                                    @RequestParam(required = false, value = "action", defaultValue = "") String action
    )
            throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {

        if (action != null && action.equals("add")) {
            List<String> statusList = Arrays.asList("Enable", "Disable");
            model.addAttribute("statusList", statusList);
            model.addAttribute("action", action);
            return "admin/foundation/foundation-form";
        } else if (action != null && action.equals("edit")) {
            Optional<Foundation>foundation = foundationService.findById(Integer.parseInt(id));
            if (foundation.isPresent())
                model.addAttribute("foundation", foundation.get());
            else
                model.addAttribute("foundation", null);
            List<String> statusList = Arrays.asList("Enable", "Disable");
            model.addAttribute("statusList", statusList);
            model.addAttribute("action", action);
            return "admin/foundation/foundation-form";
        }
        if (id == null) id = "";
        if (name == null) name = "";
        Pageable pageable = PageRequest.of(page, TOTAL_ITEMS_PER_PAGE);
        Page<Foundation> foundationList = foundationService.getPage(id, name, pageable);

        model.addAttribute("foundationList", foundationList);
        model.addAttribute("size", foundationList.getSize());
        model.addAttribute("totalPages", foundationList.getTotalPages());
        model.addAttribute("totalElements", foundationList.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "admin/foundation/foundation";
    }
    // Cập nhật Foundation
//    @GetMapping("/foundation/edit/{id}")
//    public String showEditFoundationForm(@PathVariable("id") int id, Model model) {
//        Optional<Foundation> foundationOptional = foundationService.findById(id);
//        if (foundationOptional.isPresent()) {
//            model.addAttribute("foundation", foundationOptional.get());
//            model.addAttribute("categories", categoryService.FindAll());
//            model.addAttribute("content", "/pages/FoundationEdit");
//        } else {
//            model.addAttribute("error", "Foundation not found");
//        }
//        return "index";
//    }
//
//    @PostMapping("/foundation/update/{id}")
//    public String updateFoundation(@PathVariable("id") int id, @ModelAttribute Foundation foundation, Model model) {
//        Optional<Foundation> foundationOptional = foundationService.findById(id);
//        if (foundationOptional.isPresent()) {
//            foundation.setId(id);
//            foundationService.save(foundation);
//            return "redirect:/Donations/foundation";
//        } else {
//            model.addAttribute("error", "Foundation not found");
//            return "index";
//        }
//    }

    // Xóa Foundation-
//    @DeleteMapping("/foundation/{id}")
//    public String deleteFoundation(@PathVariable("id") int id, Model model) {
//        Optional<Foundation> foundationOptional = foundationService.findById(id);
//        if (foundationOptional.isPresent()) {
//            foundationService.deleteById(id);  // Xóa Foundation theo ID
//            return "redirect:/Donations/foundation";  // Sau khi xóa xong, chuyển hướng về danh sách Foundation
//        } else {
//            model.addAttribute("error", "Foundation not found");
//            return "index";
//        }
//    }
}
