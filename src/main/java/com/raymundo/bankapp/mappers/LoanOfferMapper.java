package com.raymundo.bankapp.mappers;

import com.raymundo.bankapp.dto.LoanOfferDto;
import com.raymundo.bankapp.entities.LoanOffer;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LoanOfferMapper {

    LoanOfferDto fromEntity(LoanOffer entity);

    LoanOffer toEntity(LoanOfferDto dto);

    List<LoanOfferDto> fromEntities(Iterable<LoanOffer> entities);

    List<LoanOffer> toEntities(Iterable<LoanOfferDto> dtos);

}
