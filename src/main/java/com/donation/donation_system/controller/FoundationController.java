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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.Option;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

import static com.donation.donation_system.utils.Constants.TOTAL_ITEMS_PER_PAGE;

@Controller
@RequestMapping("Donations")
public class FoundationController {

    @Autowired
    private FundService fundService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private FoundationService foundationService;

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
    @GetMapping("/admin/foundation/add")
    public String showFoundationAdminAddForm(Model model) {
        Foundation foundation = new Foundation();
        model.addAttribute("foundation", foundation);
        List<String> statusList = Arrays.asList("Enable", "Disable");
        model.addAttribute("statusList", statusList);
        return "admin/foundation/foundation-form-add";
    }
    @PostMapping("/admin/foundation/add")
    public String processFoundation(@ModelAttribute ("foundation") Foundation foundation,Model model) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        try
        {
            if(foundation.getName()==null || foundation.getName().equals("")){
                model.addAttribute("Error", "Category name cannot be empty");
                List<String> statusList = Arrays.asList("Enable", "Disable");
                model.addAttribute("statusList", statusList);
                return "admin/foundation/foundation-form-add";
            }

            foundationService.save(foundation);

            model.addAttribute("Success", "Them thanh cong");
            return "redirect:/Donations/admin/foundation";
        } catch (Exception e) {
            model.addAttribute("Error", e.getMessage());
            List<String> statusList = Arrays.asList("Enable", "Disable");
            model.addAttribute("statusList", statusList);
            model.addAttribute("Error", "Category name cannot be empty");
            return "admin/foundation/foundation-form-add";

        }
    }

    @GetMapping("/admin/foundation/edit")
    public String showEditFoundationForm(@RequestParam("id") int id, Model model) {
        Optional<Foundation> foundation = foundationService.findById(id);
        if (!foundation.isPresent()) { // Sửa để kiểm tra Optional an toàn hơn
            model.addAttribute("error", "Foundation not found!");
            return "redirect:/admin/foundation";
        }
        model.addAttribute("foundation", foundation.get());
        List<String> statusList = Arrays.asList("Enable", "Disable");
        model.addAttribute("statusList", statusList);
        return "admin/foundation/foundation-form-edit";
    }

    @PostMapping("/admin/foundation/edit")
    public ResponseEntity<String> updateFoundation(@RequestBody Foundation foundation) {
        // Kiểm tra dữ liệu
        String validationError = validateFoundation(foundation);
        if (validationError != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
        }

        // Kiểm tra tên đã tồn tại chưa
        if (foundationService.existsByNameAndIdNot(foundation.getName(), foundation.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tên Nhà tổ chức đã tồn tại");
        }

        // Cập nhật
        boolean result = foundationService.updateFoundation(
                foundation.getName(),
                foundation.getEmail(),
                foundation.getDescription(),
                foundation.getStatus(),
                foundation.getId()
        );

        if (result) {
            return ResponseEntity.ok("Nhà tổ chức sửa thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sửa Nhà tổ chức không thành công");
        }
    }

    // Hàm kiểm tra dữ liệu đầu vào
    private String validateFoundation(Foundation foundation) {
        if (foundation.getName() == null || foundation.getName().trim().isEmpty()) {
            return "Tên không được để trống";
        }
        if (foundation.getEmail() == null || foundation.getEmail().trim().isEmpty()|| !foundation.getEmail().contains("@")) {
            return "Email không được để trống và phải đúng cú pháp Ví dụ chứa ký tự @";
        }
        if (foundation.getDescription() == null || foundation.getDescription().trim().isEmpty()) {
            return "Mô tả không được để trống";
        }
        return null;
    }

    @DeleteMapping("/admin/foundation/delete/{id}")
    public ResponseEntity<String> deleteFoundation(@PathVariable("id") int id) {
        Optional<Foundation> foundation = foundationService.findById(id);
        if (!foundation.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Foundation không tồn tại hoặc đã bị xóa");
        }

        boolean result = foundationService.deleteFoundation(id);
        return result
                ? ResponseEntity.ok("Xóa thành công")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể xóa");
    }




}


