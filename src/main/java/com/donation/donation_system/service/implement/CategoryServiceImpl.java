package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.repository.CategoryRepository;
import com.donation.donation_system.repository.DonationRepository;
import com.donation.donation_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> FindAll() {
        return categoryRepository.findAll();
    }

}
