package com.donation.donation_system.repository;

import com.donation.donation_system.model.Foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Integer> {
//    @Query("select fd from foundation where fd.status='1'")
//    List<Foundation> findAllFoundation();
    List<Foundation> findByFoundationContaining(String name);
    Foundation getFoundationById(int id);
}
