package com.donation.donation_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fund")
public class Fund {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "expectedResult")
    private int expectedResult;

    @Column(name = "status")
    private String status;

    @CreatedDate
    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "endDate")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private Foundation foundation;
}
