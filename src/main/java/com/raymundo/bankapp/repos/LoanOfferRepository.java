package com.raymundo.bankapp.repos;

import com.raymundo.bankapp.entities.LoanOffer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoanOfferRepository extends CrudRepository<LoanOffer, UUID> {

}
