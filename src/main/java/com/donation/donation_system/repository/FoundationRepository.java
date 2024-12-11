package com.donation.donation_system.repository;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Foundation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Integer> {
    @Query(value = "SELECT COUNT(*) FROM Foundation f WHERE CAST(f.id AS string) LIKE %:id% AND f.name LIKE %:name%")
    int getTotalItems(@Param("id") String id, @Param("name") String name);

    @Query("SELECT f FROM Foundation f WHERE CAST(f.id AS string) LIKE %:id% AND f.name LIKE %:name%")
    Page<Foundation> getPage(@Param("id") String id, @Param("name") String name, Pageable pageable);

    @Query("SELECT f FROM Foundation f WHERE f.name LIKE %?1% OR CAST(f.id AS string) LIKE %?1%")
    Page<Foundation> findAllByNameOrID(String query, Pageable pageable);

    @Modifying
    @Query(value = "update Foundation f set f.status= ?1 where f.id= ?2 ")
    int deleteFoundation(String status, int id);

    @Modifying
    @Query(value = "update Foundation f set f.name= :name, f.email= :email, f.description= :description, f.status= :status where f.id= :id")
    int updateFoundationInfo(@Param("name") String name, @Param("email") String email, @Param("description") String description, @Param("status") String status, @Param("id") int id);

    List<Foundation> findByNameContaining(String foundationName);

    @Query("SELECT f FROM Foundation f WHERE f.name = :foundationName")
    Foundation findByName(String foundationName);

    @Query("SELECT f FROM Foundation f WHERE f.id = :foundationId")
    Foundation findOneById(@Param("foundationId") int foundationId);
}
