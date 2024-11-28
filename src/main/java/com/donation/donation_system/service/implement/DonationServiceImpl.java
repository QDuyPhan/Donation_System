package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.Donation;
import com.donation.donation_system.repository.DonationRepository;
import com.donation.donation_system.service.DonationService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Pageable;

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

    @Override
    public Page<Donation> getPage(String id, String username, String fundName, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {

//        return donationRepository.findByIdContainingAndUser_UsernameContainingAndFund_NameContaining(id, username, fundName, pageable);
        return donationRepository.getPage(id, username, fundName, pageable);
    }

}
