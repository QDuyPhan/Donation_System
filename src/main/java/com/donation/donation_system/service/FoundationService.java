package com.donation.donation_system.service;

import com.donation.donation_system.model.Foundation;

import java.util.List;

public interface FoundationService
{
    Foundation getFoundation(int foundation_id);
    List<Foundation> getAllFoundation();
    List<Foundation> searchList(String keyword);
    void SaveFoundation(Foundation f);
    void DeleteFoundation(Foundation f);
}
