package com.donation.donation_system.controller;

import com.donation.donation_system.model.Foundation;
import com.donation.donation_system.service.FoundationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/foundations")
public class FoundationController {

    @Autowired
    private FoundationService foundationService;

    // Lấy tất cả các tổ chức
    @GetMapping
    public ResponseEntity<List<Foundation>> getAllFoundations() {
        List<Foundation> foundationList = foundationService.GetAll();
        return ResponseEntity.ok(foundationList);
    }

    // Lấy thông tin tổ chức theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Foundation> getFoundationById(@PathVariable int id) {
        Optional<Foundation> foundation = foundationService.findById(id);
        if (foundation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(foundation.get());
    }

    // Thêm mới một hoặc nhiều tổ chức
    @PostMapping
    public ResponseEntity<List<Foundation>> saveFoundations(@RequestBody List<Foundation> foundations) {
        List<Foundation> savedFoundations = foundationService.saveAll(foundations);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFoundations);
    }

    // Cập nhật tổ chức theo ID
    @PutMapping("/{id}")
    public ResponseEntity<Foundation> updateFoundation(@PathVariable int id, @RequestBody Foundation foundationDetails) {
        Optional<Foundation> foundationOptional = foundationService.findById(id);

        if (foundationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Foundation foundation = foundationOptional.get();

        // Cập nhật thông tin nếu có
        if (foundationDetails.getName() != null) {
            foundation.setName(foundationDetails.getName());
        }
        if (foundationDetails.getDescription() != null) {
            foundation.setDescription(foundationDetails.getDescription());
        }
        if (foundationDetails.getImageUrl() != null) {
            foundation.setImageUrl(foundationDetails.getImageUrl());
        }
        if (foundationDetails.getContact() != null) {
            foundation.setContact(foundationDetails.getContact());
        }

        Foundation updatedFoundation = foundationService.save(foundation);
        return ResponseEntity.ok(updatedFoundation);
    }

    // Xóa tổ chức theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoundation(@PathVariable int id) {
        Optional<Foundation> foundationOptional = foundationService.findById(id);

        if (foundationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        foundationService.DeleteById( id);
        return ResponseEntity.noContent().build();
    }
}
