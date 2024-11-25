package com.donation.donation_system.controller;

import com.donation.donation_system.model.Fund;
import com.donation.donation_system.service.FundService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fund")
public class FundController {

    private final FundService fundService;

    @Autowired
    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

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
        Optional<Fund> fund = fundService.findById(id);
        if (fund.isEmpty()) {
            // Trả về mã lỗi 404 nếu không tìm thấy quỹ
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Cập nhật các thông tin của quỹ
        if (fundDetails.getName() != null) {
            fund.get().setName(fundDetails.getName()); // Cập nhật tên quỹ
        }
        if (fundDetails.getDescription() != null) {
            fund.get().setDescription(fundDetails.getDescription()); // Cập nhật mô tả
        }
        if (fundDetails.getContent() != null) {
            fund.get().setContent(fundDetails.getContent()); // Cập nhật nội dung
        }
        if (fundDetails.getImage_url() != null) {
            fund.get().setImage_url(fundDetails.getImage_url()); // Cập nhật URL hình ảnh
        }
        if (fundDetails.getExpectedResult() != 0) {
            fund.get().setExpectedResult(fundDetails.getExpectedResult()); // Cập nhật kết quả mong đợi
        }
        if (fundDetails.getStatus() != null) {
            fund.get().setStatus(fundDetails.getStatus()); // Cập nhật trạng thái
        }
        if (fundDetails.getEndDate() != null) {
            fund.get().setEndDate(fundDetails.getEndDate()); // Cập nhật ngày kết thúc
        }

        // Cập nhật category và foundation nếu có
        if (fundDetails.getCategory() != null) {
            fund.get().setCategory(fundDetails.getCategory()); // Cập nhật category
        }
        if (fundDetails.getFoundation() != null) {
            fund.get().setFoundation(fundDetails.getFoundation()); // Cập nhật foundation
        }

        // Lưu lại quỹ đã được cập nhật
        Fund updatedFund = fundService.save(fund.orElse(null));

        // Trả về quỹ đã cập nhật với mã trạng thái 200 OK
        return ResponseEntity.ok(updatedFund);
    }


    // Xóa một quỹ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFund(@PathVariable int id) {
        try {
            fundService.deleteById(id);
            return ResponseEntity.noContent().build();  // Trả về 204 khi xóa thành công
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Trả về 404 nếu không tìm thấy quỹ
        }
    }
}
