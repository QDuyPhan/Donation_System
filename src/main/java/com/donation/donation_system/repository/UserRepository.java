package com.donation.donation_system.repository;

import com.donation.donation_system.model.Donation;
import com.donation.donation_system.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findUserById(int id);

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

    @Modifying
    @Query("update User u set u.password = :password where u.username = :username")
    int updatePassword(@Param("password") String password, @Param("username") String username);

    @Query(value = "SELECT * FROM Donation WHERE id = ?1 ORDER BY created_date DESC LIMIT ?2 OFFSET ?3", nativeQuery = true)
    List<Donation> getPageDonationListByUser(int id, int limit, int offset);

    @Query(value = "select count(*) from Donation where id = :id", nativeQuery = true)
    int getTotalDonationByUser(int id);

    User findByResetPasswordToken(String token);

//    @Query(value = "SELECT u FROM User u " +
//            "WHERE u.username LIKE %:username% " +
//            "AND u.email LIKE %:email% " +
//            "AND u.sdt LIKE %:sdt% " +
//            "AND (:role = -1 OR u.role = :role)", nativeQuery = true)
//    Page<User> getPage(String username, String email, String sdt, int role, Pageable pageable);


    @Query("SELECT u FROM User u " +
            "WHERE (:username IS NULL OR u.username LIKE %:username%) " +
            "AND (:phone IS NULL OR u.sdt LIKE %:phone%) " +
            "AND (:email IS NULL OR u.email LIKE %:email%) " +
            "AND (:role = 0 OR u.role = :role)")
    Page<User> getPage(@Param("username") String username,
                       @Param("phone") String phone,
                       @Param("email") String email,
                       @Param("role") Integer role,
                       Pageable pageable);
}
