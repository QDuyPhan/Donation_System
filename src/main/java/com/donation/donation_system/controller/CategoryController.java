package com.donation.donation_system.controller;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Foundation;
import com.donation.donation_system.model.Fund;
import com.donation.donation_system.service.CategoryService;
import com.donation.donation_system.service.DonationService;
import com.donation.donation_system.service.FundService;
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
import java.sql.SQLException;
import java.util.*;

import static com.donation.donation_system.utils.Constants.TOTAL_ITEMS_PER_PAGE;

@Controller
@RequestMapping("/Donations")
public class CategoryController {
    @Autowired
    private FundService fundService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/category")
    public String showCategory(@RequestParam(name = "id", required = false, defaultValue = "") int id, Model model) {

        List<Fund> fundList = fundService.getByCategoryId(id);
        Optional<Category> categoryOptional = categoryService.FindById(id);
        List<Category> categories = categoryService.FindAll();
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

    @GetMapping("/admin/category")
    public String showCategoryAdmin(Model model, HttpSession session,
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
            return "admin/category/category-form";
        } else if (action != null && action.equals("edit")) {
            Optional<Category>category = categoryService.FindById(Integer.parseInt(id));
            if (category.isPresent())
                model.addAttribute("category", category.get());
            else
                model.addAttribute("category", null);
            List<String> statusList = Arrays.asList("Enable", "Disable");
            model.addAttribute("statusList", statusList);
            model.addAttribute("action", action);
            return "admin/category/category-form";
        }
        if (id == null) id = "";
        if (name == null) name = "";
        Pageable pageable = PageRequest.of(page, TOTAL_ITEMS_PER_PAGE);
        Page<Category> categoryList = categoryService.getPage(id, name, pageable);
//        option
//        int totalItems = categoryService.getTotalItems(id, name);
//        int totalPages = (int) Math.ceil((double) totalItems / TOTAL_ITEMS_PER_PAGE);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("size", categoryList.getSize());
        model.addAttribute("totalPages", categoryList.getTotalPages());
        model.addAttribute("totalElements", categoryList.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "admin/category/category";
    }

    @GetMapping("/admin/category/add")
    public String showCategoryAdminAddForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        List<String> statusList = Arrays.asList("Enable", "Disable");
        model.addAttribute("statusList", statusList);
        return "admin/category/category-form-add";
    }

    @PostMapping("/admin/category/add")
    public String processCategory(@ModelAttribute("category") Category category, Model model) throws SQLException, NoSuchAlgorithmException, ClassNotFoundException {
        try
        {
            if(category.getName()==null || category.getName().equals("")){
                model.addAttribute("Error", "Category name cannot be empty");
                List<String> statusList = Arrays.asList("Enable", "Disable");
                model.addAttribute("statusList", statusList);
                return "admin/category/category-form-add";
            }

            categoryService.save(category);
            model.addAttribute("Success", "Them thanh cong");
            return "redirect:/admin/category";
        } catch (Exception e) {
            model.addAttribute("Error", e.getMessage());
            List<String> statusList = Arrays.asList("Enable", "Disable");
            model.addAttribute("statusList", statusList);
            return "admin/category/category-form-add";
        }
    }
    @GetMapping("/admin/category/edit")
    public String showEditCategoryAdminForm(@RequestParam("id")int id, Model model) {
        Optional<Category> category = categoryService.FindById(id);
        if (category==null) {
            model.addAttribute("Error", "Category not found");
            return "redirect:/admin/category";
        }
        model.addAttribute("category", category.get());
        List<String> statusList = Arrays.asList("Enable", "Disable");
        model.addAttribute("statusList", statusList);
        return "admin/category/category-form-edit";
    }
    @PostMapping("/admin/category/edit")
    public ResponseEntity<String> updateCategory(@RequestBody Category category) {
        String validationError = validateCategory(category);
        if (validationError != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
        }

        // Kiểm tra tên đã tồn tại chưa
        if (categoryService.existsByNameAndIdNot(category.getName(), category.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tên Danh mục đã tồn tại");
        }
        boolean result = categoryService.updateCategory(
                category.getName(),
                category.getDescription(),
                category.getStatus(),
                category.getId()
        );
        if(result){
            return ResponseEntity.ok("Success");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed");
        }
    }
    @PostMapping("/admin/category/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam("id")int id) {
        boolean result = categoryService.deleteCategory(id);
        if(result){
            return ResponseEntity.ok("Success");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed");
        }
    }

    private String validateCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return "Tên không được để trống";
        }
        if (category.getDescription() == null || category.getDescription().trim().isEmpty()) {
            return "Mô tả không được để trống";
        }
        return null;
    }
}
