package com.donation.donation_system.repository;

import com.donation.donation_system.model.Donation;
import com.donation.donation_system.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
            "FROM Donation d "+
            "WHERE d.fund.id = :fundId ")
    List<Donation> findDonationByFund(@Param("fundId") int fundId);
}
