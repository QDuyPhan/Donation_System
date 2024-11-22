package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.repository.CategoryRepository;
import com.donation.donation_system.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Category> FindById(int id) {
        return categoryRepository.findById(id);
    }

}
