package com.donation.donation_system.repository;

import com.donation.donation_system.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FundRepository extends JpaRepository<Fund, Integer> {
}
