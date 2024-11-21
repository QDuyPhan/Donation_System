package com.donation.donation_system.repository;

import com.donation.donation_system.model.Fund;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FundRepository extends JpaRepository<Fund, Integer> {

    @Query("SELECT f FROM Fund f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Fund> getAllBySearch(@Param("search") String search);


}
