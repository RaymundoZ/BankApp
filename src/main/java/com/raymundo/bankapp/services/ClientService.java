package com.raymundo.bankapp.services;

import com.raymundo.bankapp.dto.ClientDto;
import com.raymundo.bankapp.mappers.ClientMapper;
import com.raymundo.bankapp.repos.ClientRepository;
import com.raymundo.bankapp.specifications.ClientSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService implements com.raymundo.bankapp.services.Service<ClientDto, UUID> {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    @Autowired
    public ClientService(ClientRepository repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(ClientDto clientDto) {
        repository.save(mapper.toEntity(clientDto));
    }

    @Override
    public void update(ClientDto clientDto) {
        create(clientDto);
    }

    @Override
    public ClientDto get(UUID uuid) {
        return mapper.fromEntity(repository.findById(uuid).orElse(null));
    }

    @Override
    public List<ClientDto> getAll() {
        return mapper.fromEntities(repository.findAll());
    }

    @Override
    public void delete(UUID uuid) {
        repository.deleteById(uuid);
    }

    public List<ClientDto> getByText(String text) {
        if (text == null || text.isEmpty())
            return getAll();
        return mapper.fromEntities(repository.findAll(ClientSpecification.getByText(text)));
    }

    public ClientRepository getRepository() {
        return repository;
    }

    public ClientMapper getMapper() {
        return mapper;
    }
}
