package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.Donation;
import com.donation.donation_system.repository.DonationRepository;
import com.donation.donation_system.service.DonationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;

    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Override
    public Integer findTotalDonationsByFund(int id) {
        System.out.println("Fetching total donations for fund ID: " + id);
        Integer totalDonations = donationRepository.findTotalDonationsByFund(id);
        System.out.println(" total : " + totalDonations);
        return totalDonations != null ? totalDonations : 0; // Nếu không có kết quả, trả về 0
    }

    @Override
    public Integer countDonationsByFund(int id) {
        System.out.println("Fetching total donations for fund ID: " + id);
        Integer Donations = donationRepository.countDonationsByFund(id);
        System.out.println(" sum : " + Donations);
        return Donations != null ? Donations : 0; // Nếu không có kết quả, trả về 0
    }

    @Override
    public List<Donation> findDonationById(int id) {
        return donationRepository.findDonationByFund(id);
    }

}
