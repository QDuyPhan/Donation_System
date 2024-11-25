package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.Foundation;
import com.donation.donation_system.repository.FoundationRepository;
import com.donation.donation_system.service.FoundationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoundationServiceImpl implements FoundationService {
    private final FoundationRepository foundationRepository;

    public FoundationServiceImpl(FoundationRepository foundationRepository) {
        this.foundationRepository = foundationRepository;
    }

    @Override
    public List<Foundation> findAll() {
        return foundationRepository.findAll();
    }
}

