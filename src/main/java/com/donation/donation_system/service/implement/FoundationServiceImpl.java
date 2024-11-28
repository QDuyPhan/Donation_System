package com.donation.donation_system.service.implement;

import com.donation.donation_system.service.FoundationService;
import com.donation.donation_system.model.Foundation;
import com.donation.donation_system.repository.FoundationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class FoundationServiceImpl implements FoundationService {

    private final FoundationRepository foundationRepository;

    @Autowired
    public FoundationServiceImpl(FoundationRepository foundationRepository) {
        this.foundationRepository = foundationRepository;
    }

    @Override
    public List<Foundation> findAll() {
        // Lấy danh sách tất cả các Foundation từ cơ sở dữ liệu
        return foundationRepository.findAll();
    }

    @Override
    public Optional<Foundation> findById(int id) {
        // Tìm Foundation theo ID
        return foundationRepository.findById(id);
    }
    @Override
    public int getTotalItems(String id, String name) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        int count = foundationRepository.getTotalItems(id, name);
        return count;
    }
    @Override
    public Page<Foundation> getPage(String id, String name, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        return foundationRepository.getPage(id, name, pageable);
    }

    @Override
    public Page<Foundation> findAllByNameOrID(String query, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        return foundationRepository.findAllByNameOrID(query, pageable);
    }
    @Override
    public Foundation save(Foundation foundation) {
        // Lưu hoặc cập nhật Foundation vào cơ sở dữ liệu
        return foundationRepository.save(foundation);
    }

    @Override
    public void deleteById(int id) {
        // Xóa Foundation theo ID
        foundationRepository.deleteById(id);
    }
}
