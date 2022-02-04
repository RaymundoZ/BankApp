package com.raymundo.bankapp.services;

import com.raymundo.bankapp.dto.CreditDto;
import com.raymundo.bankapp.mappers.CreditMapper;
import com.raymundo.bankapp.repos.CreditRepository;
import com.raymundo.bankapp.specifications.CreditSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditService implements com.raymundo.bankapp.services.Service<CreditDto, UUID> {

    private final CreditRepository repository;
    private final CreditMapper mapper;

    @Autowired
    public CreditService(CreditRepository repository, CreditMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(CreditDto creditDto) {
        repository.save(mapper.toEntity(creditDto));
    }

    @Override
    public void update(CreditDto creditDto) {
        create(creditDto);
    }

    @Override
    public CreditDto get(UUID uuid) {
        return mapper.fromEntity(repository.findById(uuid).orElse(null));
    }

    @Override
    public List<CreditDto> getAll() {
        return mapper.fromEntities(repository.findAll());
    }

    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    public List<CreditDto> getByText(String text) {
        if (text == null || text.isEmpty())
            return getAll();
        return mapper.fromEntities(repository.findAll(CreditSpecification.getByText(text)));
    }

    public CreditRepository getRepository() {
        return repository;
    }

    public CreditMapper getMapper() {
        return mapper;
    }
}
