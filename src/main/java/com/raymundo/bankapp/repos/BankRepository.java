package com.raymundo.bankapp.repos;

import com.raymundo.bankapp.entities.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankRepository extends CrudRepository<Bank, UUID> {

}
