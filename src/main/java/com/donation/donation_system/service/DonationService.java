package com.donation.donation_system.service;

import com.donation.donation_system.model.Donation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DonationService {
    Integer findTotalDonationsByFund(int id);
    Integer countDonationsByFund(int id);
    List<Donation> findDonationById(int id);
}
