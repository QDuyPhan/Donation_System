package com.donation.donation_system.repository;

import com.donation.donation_system.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Modifying
    @Query(value = "update Category  c set c.name= :name, c.description= :description, c.status= :status where c.id= :id")
    int updateCategoryInfo(@Param("name") String name, @Param("description") String description,@Param("status")String status, @Param("id") int id);
//    Category findById(int id);
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Category c WHERE c.name = ?1 AND c.id <> ?2")
    boolean existsByNameAndIdNot(String name, int id);
}
