package com.donation.donation_system.service;

import com.donation.donation_system.model.Foundation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FoundationService {
    List<Foundation> findAll();
    Optional<Foundation> findById(int id);
    Foundation save(Foundation foundation);
    int getTotalItems(String id, String name) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
    void deleteById(int id);
    Page<Foundation>getPage(String id, String name, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
    Page<Foundation> findAllByNameOrID(String query, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
    boolean updateFoundation(String name,String email,String description,String status,int id);
    boolean deleteFoundation(int id);
    boolean existsByNameAndIdNot(String name, int id);
}
