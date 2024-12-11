package com.donation.donation_system.service;

import com.donation.donation_system.model.Fund;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FundService {
    List<Fund> FindAll();
    List<Fund> FindAllById(List<Integer> id);
    Optional<Fund> findById(int id);

    Fund save(Fund fund);

    List<Fund> saveAllAndFlush(Iterable<Fund> funds);

    void flush();

    void delete(Fund fund);

    void deleteById(Integer id);

    Fund getFundById(int id);
    Fund update(Fund fund);

    List<Fund> getAllBySearch(String search);

    List<Fund> getByCategoryId(int categoryId);

    List<Fund> getByFoundationId(int foundationId);

    Page<Fund> getPage(String id, String name, String foundation, String category, Pageable pageable) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
}
