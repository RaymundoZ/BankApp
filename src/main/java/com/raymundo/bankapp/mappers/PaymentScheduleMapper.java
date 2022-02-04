package com.raymundo.bankapp.mappers;

import com.raymundo.bankapp.dto.PaymentScheduleDto;
import com.raymundo.bankapp.entities.PaymentSchedule;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PaymentScheduleMapper {

    PaymentScheduleDto fromEntity(PaymentSchedule entity);

    PaymentSchedule toEntity(PaymentScheduleDto dto);

    List<PaymentScheduleDto> fromEntities(Iterable<PaymentSchedule> entities);

    List<PaymentSchedule> toEntities(Iterable<PaymentScheduleDto> dtos);

}
