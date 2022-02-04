package com.raymundo.bankapp.services;

import com.raymundo.bankapp.dto.LoanOfferDto;
import com.raymundo.bankapp.mappers.LoanOfferMapper;
import com.raymundo.bankapp.repos.LoanOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LoanOfferService implements com.raymundo.bankapp.services.Service<LoanOfferDto, UUID> {

    private final LoanOfferRepository repository;
    private final LoanOfferMapper mapper;

    @Autowired
    public LoanOfferService(LoanOfferRepository repository, LoanOfferMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(LoanOfferDto dto) {
        repository.save(mapper.toEntity(dto));
    }

    @Override
    public void update(LoanOfferDto dto) {
        create(dto);
    }

    @Override
    public LoanOfferDto get(UUID id) {
        return mapper.fromEntity(repository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public List<LoanOfferDto> getAll() {
        return mapper.fromEntities(repository.findAll());
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public LoanOfferRepository getRepository() {
        return repository;
    }

    public LoanOfferMapper getMapper() {
        return mapper;
    }
}
