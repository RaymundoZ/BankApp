package com.raymundo.bankapp.services;

import com.raymundo.bankapp.dto.PaymentScheduleDto;
import com.raymundo.bankapp.mappers.PaymentScheduleMapper;
import com.raymundo.bankapp.repos.PaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentScheduleService implements com.raymundo.bankapp.services.Service<PaymentScheduleDto, UUID> {

    private final PaymentScheduleRepository repository;
    private final PaymentScheduleMapper mapper;

    @Autowired
    public PaymentScheduleService(PaymentScheduleRepository repository, PaymentScheduleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(PaymentScheduleDto dto) {
        repository.save(mapper.toEntity(dto));
    }

    @Override
    public void update(PaymentScheduleDto dto) {
        create(dto);
    }

    @Override
    public PaymentScheduleDto get(UUID id) {
        return mapper.fromEntity(repository.findById(id).orElse(null));
    }

    @Override
    public List<PaymentScheduleDto> getAll() {
        return mapper.fromEntities(repository.findAll());
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public PaymentScheduleRepository getRepository() {
        return repository;
    }

    public PaymentScheduleMapper getMapper() {
        return mapper;
    }
}
