package com.raymundo.bankapp.repos;

import com.raymundo.bankapp.entities.Credit;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditRepository extends CrudRepository<Credit, UUID>, JpaSpecificationExecutor<Credit> {

}
