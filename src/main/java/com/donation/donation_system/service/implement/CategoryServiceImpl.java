package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.Category;
import com.donation.donation_system.repository.CategoryRepository;
import com.donation.donation_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static utils.Constants.STATUS_ENABLE;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> FindAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> FindById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public int getTotalItems(String id, String name) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        int count = categoryRepository.getTotalItems(id, name);
        return count;
    }

    @Override
    public Page<Category> getPage(String id, String name, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        return categoryRepository.getPage(id, name, pageable);
    }

    @Override
    public Page<Category> findAllByNameOrID(String query, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        return categoryRepository.findAllByNameOrID(query, pageable);
    }

    @Override
    public Category save(Category category) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> search(String categoryName) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        return categoryRepository.search(categoryName, STATUS_ENABLE);
    }

    @Override
    public List<Category> findAll() throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        return categoryRepository.findAll();
    }

    @Override
    public Category findByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

}
