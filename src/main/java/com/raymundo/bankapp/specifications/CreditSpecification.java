package com.raymundo.bankapp.specifications;

import com.raymundo.bankapp.entities.Credit;
import org.springframework.data.jpa.domain.Specification;

public class CreditSpecification {

    public static Specification<Credit> getByText(String text) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(criteriaBuilder.like(root.get("creditLimit").as(String.class), text.trim() + "%"),
                        criteriaBuilder.like(root.get("interestRate").as(String.class), text.trim() + "%"));
    }

}
