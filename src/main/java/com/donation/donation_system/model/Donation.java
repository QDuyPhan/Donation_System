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
@Table(name = "donation")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "donation_Amount")
    private int donationAmount;

    @Column(name = "donation_Message")
    private String donationMessage;

    @ManyToOne
    @JoinColumn(name = "fund_id")
    private Fund fund;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(name = "created_Date")
    private Date createdDate;


    public Donation(int fundId, int totalAmount) {
        this.id = fundId;
        this.donationAmount = totalAmount;
    }
}
