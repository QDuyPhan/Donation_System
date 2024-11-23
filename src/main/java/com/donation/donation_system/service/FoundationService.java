package com.donation.donation_system.service;

import com.donation.donation_system.model.Foundation;

import java.util.List;
import java.util.Optional;

public interface FoundationService {
    Foundation getFoundation(int foundation_id);
    Foundation save(Foundation foundation);
    List<Foundation> GetAll();

    List<Foundation> searchList(String keyword);

    void SaveFoundation(Foundation f);

    void DeleteFoundation(Foundation f);
    void DeleteById(int id);
    Optional<Foundation> findById(int id);
}

