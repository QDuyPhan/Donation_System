package com.donation.donation_system.service;

import com.donation.donation_system.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    List<Category> FindAll();
    Optional<Category> FindById(int id);
}
