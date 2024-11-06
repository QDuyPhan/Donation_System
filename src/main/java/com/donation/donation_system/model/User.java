package com.donation.donation_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private int role;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email")
    private String email;

    @Column(name = "diachi")
    private String diachi;

    @Column(name = "status")
    private String status;
}
