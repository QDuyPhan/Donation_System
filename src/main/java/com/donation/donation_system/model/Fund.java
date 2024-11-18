package com.donation.donation_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fund")
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing primary key
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false) // Non-nullable field
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "image_url")
    private String imageUrl; // CamelCase for consistency

    @Column(name = "expected_result")
    private Integer expectedResult; // Integer for nullable integer values

    @Column(name = "status")
    private String status;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp // Automatically sets the creation timestamp
    private LocalDateTime createdDate;

    @Column(name = "end_date")
    private LocalDateTime endDate; // Store both date and time

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true) // Foreign key to Category
    private Category category;

    @ManyToOne
    @JoinColumn(name = "foundation_id", nullable = true) // Foreign key to Foundation
    private Foundation foundation;
}
