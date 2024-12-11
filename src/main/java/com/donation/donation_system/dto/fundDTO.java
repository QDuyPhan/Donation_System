package com.donation.donation_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
public class fundDTO {

    @Size(min = 5, max = 50)
    private String name;         // Tên của quỹ
    private String description;  // Mô tả ngắn
    private String content;      // Nội dung chi tiết
    private String imageUrl;     // URL của ảnh đại diện
    private Double expectedResult; // Mục tiêu quyên góp

    @NotBlank
    private LocalDate endDate;   // Ngày kết thúc chiến dịch


}

