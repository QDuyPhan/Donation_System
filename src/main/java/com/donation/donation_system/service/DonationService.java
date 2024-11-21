package com.donation.donation_system.service;

import org.springframework.stereotype.Service;

@Service
public interface DonationService {
    Integer findTotalDonationsByFund(int id);
    Integer countDonationsByFund(int id);
}
