package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.Foundation;
import com.donation.donation_system.repository.FoundationRepository;
import com.donation.donation_system.service.FoundationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoundationServiceImpl implements FoundationService {
    @Autowired
    private FoundationRepository foundationRepository;

    @Override
    public Foundation getFoundation(int foudation_id) {
        return foundationRepository.getFoundationById(foudation_id);
    }

    @Override
    public Foundation save(Foundation foundation) {
        return null;
    }

    @Override
    public List<Foundation> GetAll() {
        return foundationRepository.findAll();
    }

    @Override
    public List<Foundation> searchList(String keyword) {
        return foundationRepository.findByName(keyword);
    }

    @Override
    public void SaveFoundation(Foundation f)
    {
        foundationRepository.save(f);
    }

    @Override
    public void DeleteFoundation(Foundation f) {
        foundationRepository.delete(f);
    }

    @Override
    public void DeleteById(int id) {

    }

    @Override
    public Optional<Foundation> findById(int id) {
        return Optional.empty();
    }
}
