package com.donation.donation_system.repository;

import com.donation.donation_system.model.Donation;
import com.donation.donation_system.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {

    @Query("SELECT SUM(d.donationAmount) " +
            "FROM Donation d " +
            "WHERE d.fund.id = :fundId")
    Integer findTotalDonationsByFund(@Param("fundId") int fundId);

    @Query("SELECT count(d.donationAmount) " +
            "FROM Donation d " +
            "WHERE d.fund.id = :fundId")
    Integer countDonationsByFund(@Param("fundId") int fundId);

    @Query("SELECT d " +
            "FROM Donation d " +
            "WHERE d.fund.id = :fundId ")
    List<Donation> findDonationByFund(@Param("fundId") int fundId);

    List<Donation> findByIdContainingAndUser_UsernameContainingAndFund_NameContaining(String id, String username, String fundName, Pageable pageable);

    @Query("SELECT d FROM Donation d " +
            "WHERE CAST(d.id AS string) LIKE %:id% " +
            "AND d.user.username LIKE %:username% " +
            "AND d.fund.name LIKE %:fundName%")
    Page<Donation> getPage(@Param("id") String id,
                           @Param("username") String username,
                           @Param("fundName") String fundName,
                           Pageable pageable);

    Page<Donation> findByUserId(int userId, Pageable pageable);

}
