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

    @Query(value = "SELECT * FROM Fund f WHERE LOWER(CONVERT(f.name USING utf8)) LIKE LOWER(CONCAT('%', :search, '%'))", nativeQuery = true)
    List<Fund> getAllBySearch(@Param("search") String search);

    @Query(value = "SELECT f FROM Fund f WHERE f.category.id = :categoryId")
    List<Fund> getByCategoryId(@Param("categoryId") int categoryId);

    @Query(value = "SELECT f FROM Fund f WHERE f.foundation.id = :foundationId")
    List<Fund> getByFoundationId(@Param("foundationId") int foundationId);
}
