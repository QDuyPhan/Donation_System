package com.donation.donation_system.service;

import com.donation.donation_system.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    List<Category> FindAll();

    Optional<Category> FindById(int id);

    int getTotalItems(String id, String name) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

    Page<Category> getPage(String id, String name, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

    Page<Category> findAllByNameOrID(String query, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;

//    Category findById(int id) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
}
