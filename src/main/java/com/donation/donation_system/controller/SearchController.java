package com.donation.donation_system.controller;

import com.donation.donation_system.model.Fund;
import com.donation.donation_system.service.FundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Donations")
public class SearchController {

    private final FundService fundService;

    public SearchController(FundService fundService) {
        this.fundService = fundService;
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        // Thêm từ khóa vào model để truyền về view
        model.addAttribute("keyword", keyword);

        // Thực hiện logic tìm kiếm (gọi service, query database)
        List<Fund> fundList = fundService.getAllBySearch(keyword);
        model.addAttribute("fundList", fundList);
        model.addAttribute("content", "/pages/SearchResult");

        // Trả về tên template để hiển thị kết quả
        return "index"; // Tên file HTML trong thư mục templates
    }

    @GetMapping("/searchTest")
    @ResponseBody
    public List<Fund> getAllBySearch(@RequestParam(name = "keyword", required = false) String keyword) {
        // Gọi service để lấy danh sách kết quả tìm kiếm
        return fundService.getAllBySearch(keyword);  // Trả về danh sách dưới dạng JSON
    }
}


