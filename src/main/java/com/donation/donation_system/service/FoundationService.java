package com.donation.donation_system.service;

import com.donation.donation_system.model.Foundation;

import java.util.List;
import java.util.Optional;

public interface FoundationService {
    List<Foundation> findAll();
    Optional<Foundation> findById(int id);
    Foundation save(Foundation foundation);
    void deleteById(int id);
}
