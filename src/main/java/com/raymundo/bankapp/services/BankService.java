package com.raymundo.bankapp.services;

import com.raymundo.bankapp.dto.BankDto;
import com.raymundo.bankapp.mappers.BankMapper;
import com.raymundo.bankapp.repos.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BankService implements com.raymundo.bankapp.services.Service<BankDto, UUID> {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    @Autowired
    public BankService(BankRepository bankRepository, BankMapper bankMapper) {
        this.bankRepository = bankRepository;
        this.bankMapper = bankMapper;
    }

    @Override
    public void create(BankDto bankDto) {
        bankRepository.save(bankMapper.toEntity(bankDto));
    }

    @Override
    public void update(BankDto bankDto) {
        create(bankDto);
    }

    @Override
    public BankDto get(UUID uuid) {
        return bankMapper.fromEntity(bankRepository.findById(uuid).orElse(null));
    }

    @Override
    @Transactional
    public List<BankDto> getAll() {
        return bankMapper.fromEntities(bankRepository.findAll());
    }

    @Override
    public void delete(UUID uuid) {
        bankRepository.deleteById(uuid);
    }

    public BankRepository getBankRepository() {
        return bankRepository;
    }

    public BankMapper getBankMapper() {
        return bankMapper;
    }

}
