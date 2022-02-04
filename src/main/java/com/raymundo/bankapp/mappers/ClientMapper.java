package com.raymundo.bankapp.mappers;

import com.raymundo.bankapp.dto.ClientDto;
import com.raymundo.bankapp.entities.Client;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClientMapper {

    ClientDto fromEntity(Client entity);

    Client toEntity(ClientDto dto);

    List<ClientDto> fromEntities(Iterable<Client> entities);

    List<Client> toEntities(Iterable<ClientDto> dtos);

}
