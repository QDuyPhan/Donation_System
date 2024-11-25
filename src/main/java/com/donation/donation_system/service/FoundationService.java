package com.donation.donation_system.service;

import com.donation.donation_system.model.Foundation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FoundationService {

    List<Foundation> findAll();
}
