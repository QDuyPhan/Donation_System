package com.donation.donation_system.service;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.model.Fund;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> FindAll();
}
