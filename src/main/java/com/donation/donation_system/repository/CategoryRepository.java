package com.donation.donation_system.repository;

import com.donation.donation_system.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT COUNT(*) FROM Category c WHERE CAST(c.id AS string) LIKE %:id% AND c.name LIKE %:name%")
    int getTotalItems(@Param("id") String id, @Param("name") String name);

//    @Query(value = "SELECT * FROM Category c WHERE c.id LIKE %:id% AND c.name LIKE %:name%", nativeQuery = true)
//    Page<Category> getPage(@Param("id") String id, @Param("name") String name, Pageable pageable);

    @Query("SELECT c FROM Category c WHERE CAST(c.id AS string) LIKE %:id% AND c.name LIKE %:name%")
    Page<Category> getPage(@Param("id") String id, @Param("name") String name, Pageable pageable);

    @Query("SELECT d FROM Category d WHERE d.name LIKE %?1% OR CAST(d.id AS string) LIKE %?1%")
    Page<Category> findAllByNameOrID(String query, Pageable pageable);


    @Query("SELECT c FROM Category c WHERE c.name LIKE %:categoryName% AND c.status = :status")
    List<Category> search(String categoryName, String status);
//    Category findById(int id);

}
