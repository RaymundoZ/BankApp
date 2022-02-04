package com.raymundo.bankapp.repos;

import com.raymundo.bankapp.entities.PaymentSchedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentScheduleRepository extends CrudRepository<PaymentSchedule, UUID> {

}
