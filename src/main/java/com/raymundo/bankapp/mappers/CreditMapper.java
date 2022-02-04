package com.raymundo.bankapp.mappers;

import com.raymundo.bankapp.dto.CreditDto;
import com.raymundo.bankapp.entities.Credit;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CreditMapper {

    CreditDto fromEntity(Credit entity);

    Credit toEntity(CreditDto dto);

    List<CreditDto> fromEntities(Iterable<Credit> entities);

    List<Credit> toEntities(Iterable<CreditDto> dtos);

}
