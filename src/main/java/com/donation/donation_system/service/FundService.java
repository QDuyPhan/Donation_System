package com.donation.donation_system.service;

import com.donation.donation_system.model.Fund;
import com.donation.donation_system.repository.FundRepository;

import java.util.List;
import java.util.Optional;

public interface FundService {
    List<Fund> FindAll();
    Optional<Fund> findById(int id);
    Fund save(Fund fund);
    List<Fund> saveAllAndFlush(Iterable<Fund> funds);
    void flush();
    void delete(Fund fund);
    void deleteById(Integer id);
    void deleteAll();
    void deleteAllInBatch(Iterable<Fund> funds);
    Fund saveAndFlush(Fund fund);
    List<Fund>  getAllBySearch(String search);
}
