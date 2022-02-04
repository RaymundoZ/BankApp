package com.raymundo.bankapp.mappers;

import com.raymundo.bankapp.dto.BankDto;
import com.raymundo.bankapp.entities.Bank;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BankMapper {

    BankDto fromEntity(Bank entity);

    Bank toEntity(BankDto dto);

    List<BankDto> fromEntities(Iterable<Bank> entities);

    List<Bank> toEntities(Iterable<BankDto> dtos);

}
