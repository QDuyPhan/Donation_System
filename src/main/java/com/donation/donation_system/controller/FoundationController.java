package com.donation.donation_system.controller;

import com.donation.donation_system.model.Foundation;
import com.donation.donation_system.service.FoundationService;
import com.donation.donation_system.service.FundService;
import com.donation.donation_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Donations")
public class FoundationController {

    private final FoundationService foundationService;
    private final FundService fundService;
    private final CategoryService categoryService;

    @Autowired
    public FoundationController(FoundationService foundationService, FundService fundService, CategoryService categoryService) {
        this.foundationService = foundationService;
        this.fundService = fundService;
        this.categoryService = categoryService;
    }

    // Hiển thị danh sách tất cả các Foundation
    @GetMapping("/foundation")
    public String showFoundations(Model model) {
        List<Foundation> foundations = foundationService.findAll();  // Lấy tất cả các Foundation từ service
        model.addAttribute("foundations", foundations);
        model.addAttribute("categories", categoryService.FindAll());  // Lấy danh sách Category để sử dụng nếu cần
        model.addAttribute("content", "/pages/FoundationList");  // Đây là đường dẫn tới trang hiển thị danh sách Foundation
        return "index";
    }

    // Hiển thị chi tiết Foundation
    @GetMapping("/foundation/{id}")
    public String showFoundationDetails(@PathVariable("id") int id, Model model) {
        Optional<Foundation> foundationOptional = foundationService.findById(id);
        if (foundationOptional.isPresent()) {
            Foundation foundation = foundationOptional.get();
            model.addAttribute("foundation", foundation);
            model.addAttribute("funds", fundService.findById(id));  // Lấy danh sách Quỹ liên kết với Foundation
            model.addAttribute("content", "/pages/FoundationDetail");  // Trang hiển thị chi tiết Foundation
        } else {
            model.addAttribute("error", "Foundation not found");
        }
        return "index";
    }

    // Thêm mới Foundation
    @GetMapping("/foundation/new")
    public String showCreateFoundationForm(Model model) {
        model.addAttribute("foundation", new Foundation());  // Tạo đối tượng Foundation mới để hiển thị trong form
        model.addAttribute("categories", categoryService.FindAll());  // Cung cấp các category nếu cần
        model.addAttribute("content", "/pages/FoundationCreate");  // Đường dẫn tới trang tạo mới Foundation
        return "index";
    }

    @PostMapping("/foundation")
    public String createFoundation(@ModelAttribute Foundation foundation, Model model) {
        Foundation createdFoundation = foundationService.save(foundation);
        model.addAttribute("foundation", createdFoundation);  // Hiển thị Foundation vừa tạo
        return "redirect:/Donations/foundation";  // Sau khi tạo xong, chuyển hướng về danh sách Foundation
    }

    // Cập nhật Foundation
    @GetMapping("/foundation/edit/{id}")
    public String showEditFoundationForm(@PathVariable("id") int id, Model model) {
        Optional<Foundation> foundationOptional = foundationService.findById(id);
        if (foundationOptional.isPresent()) {
            model.addAttribute("foundation", foundationOptional.get());  // Lấy đối tượng Foundation và hiển thị trong form
            model.addAttribute("categories", categoryService.FindAll());
            model.addAttribute("content", "/pages/FoundationEdit");
        } else {
            model.addAttribute("error", "Foundation not found");
        }
        return "index";
    }

    @PostMapping("/foundation/update/{id}")
    public String updateFoundation(@PathVariable("id") int id, @ModelAttribute Foundation foundation, Model model) {
        Optional<Foundation> foundationOptional = foundationService.findById(id);
        if (foundationOptional.isPresent()) {
            foundation.setId(id);  // Đảm bảo rằng ID của foundation không bị thay đổi
            foundationService.save(foundation);  // Cập nhật Foundation
            return "redirect:/Donations/foundation";  // Sau khi cập nhật xong, chuyển hướng về danh sách Foundation
        } else {
            model.addAttribute("error", "Foundation not found");
            return "index";
        }
    }

    // Xóa Foundation
    @DeleteMapping("/foundation/{id}")
    public String deleteFoundation(@PathVariable("id") int id, Model model) {
        Optional<Foundation> foundationOptional = foundationService.findById(id);
        if (foundationOptional.isPresent()) {
            foundationService.deleteById(id);  // Xóa Foundation theo ID
            return "redirect:/Donations/foundation";  // Sau khi xóa xong, chuyển hướng về danh sách Foundation
        } else {
            model.addAttribute("error", "Foundation not found");
            return "index";
        }
    }
}
