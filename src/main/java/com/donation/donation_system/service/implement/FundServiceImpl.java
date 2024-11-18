package com.donation.donation_system.service.implement;

import com.donation.donation_system.model.Fund;
import com.donation.donation_system.repository.FundRepository;
import com.donation.donation_system.service.FundService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FundServiceImpl implements FundService {

    private final FundRepository fundRepository;

    public FundServiceImpl(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }
    @Override
    public List<Fund> FindAll() {
        return fundRepository.findAll();
    }

    // Sử dụng phương thức findById có sẵn trong JpaRepository
    @Override
    public Optional<Fund> findById(int id) {
        return fundRepository.findById(Math.toIntExact(id));  // Không cần phải tự định nghĩa lại
    }

    @Override
    public Fund save(Fund fund) {
        return fundRepository.save(fund);
    }

    @Override
    public List<Fund> saveAllAndFlush(Iterable<Fund> funds) {
        return fundRepository.saveAllAndFlush(funds);
    }

    @Override
    public void flush() {
        fundRepository.flush();
    }

    @Override
    public void delete(Fund fund) {
        fundRepository.delete(fund);
    }

    @Override
    public void deleteById(Integer id) {
        if (fundRepository.existsById(id)) {
            fundRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Fund with id " + id + " not found");
        }
    }

    @Override
    public void deleteAll() {
        fundRepository.deleteAll();
    }

    @Override
    public void deleteAllInBatch(Iterable<Fund> funds) {
        fundRepository.deleteAllInBatch(funds);
    }

    @Override
    public Fund saveAndFlush(Fund fund) {
        return fundRepository.saveAndFlush(fund);
    }
}
