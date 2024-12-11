package com.donation.donation_system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

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

    @Setter
    @Getter
    @Column(name = "status")
    private String status;

    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP) // Save as Date + Time
    @CreationTimestamp // Automatically sets the creation timestamp
    private Date createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP) // Save as Date + Time
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true) // Foreign key to Category
    private Category category;

    @ManyToOne
    @JoinColumn(name = "foundation_id", nullable = true) // Foreign key to Foundation
    private Foundation foundation;

    @Setter
    @Getter
    @Transient
    private double percentAchieved;

}
