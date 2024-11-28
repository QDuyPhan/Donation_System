package com.donation.donation_system.service;

import com.donation.donation_system.model.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@Service
public interface DonationService {
    Integer findTotalDonationsByFund(int id);

    Integer countDonationsByFund(int id);

    List<Donation> findDonationById(int id);

    Page<Donation> getPage(String id, String username, String fundName, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

    Donation save(Donation donation) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
}
