package com.donation.donation_system.repository;

import com.donation.donation_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Query(value = "select count(*) from User where username = :username and id = :id")
    int activate(@Param("username") String username, @Param("id") String id);

    @Modifying
    @Query("update User u set u.status = ?1 where u.id = ?2")
    int updateStatusAfterActivated(String status, int id);

    @Query(value = "select count(*) from User where username = :username and password = :password")
    int check(@Param("username") String username, @Param("password") String password);

    @Modifying
    @Query("update User u set u.fullName = :fullname, u.email = :email, u.sdt = :sdt, u.diachi = :diachi where u.username = :username")
    int updateUserInfo(@Param("username") String username, @Param("fullname") String fullname, @Param("email") String email, @Param("sdt") String sdt, @Param("diachi") String diachi);
}
