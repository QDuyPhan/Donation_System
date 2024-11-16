package com.donation.donation_system.repository;

import com.donation.donation_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import static utils.Constants.*;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Query(value = "select count(*) from User where username = :username and id = :id")
    int activate(@Param("username") String username, @Param("id") String id);

    @Modifying
    @Query("update User u set u.status = ?1 where u.id = ?2")
    int updateStatusAfterActivated(String status, int id);
}
