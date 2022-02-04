package com.raymundo.bankapp.repos;

import com.raymundo.bankapp.entities.Client;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends CrudRepository<Client, UUID>, JpaSpecificationExecutor<Client> {


}
