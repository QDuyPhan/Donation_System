package com.donation.donation_system.repository;

import com.donation.donation_system.model.Foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Integer> {
}
