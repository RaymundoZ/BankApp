package com.raymundo.bankapp.specifications;

import com.raymundo.bankapp.entities.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    public static Specification<Client> getByText(String text) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.or(criteriaBuilder.like(root.get("name"), text.trim() + "%"),
                        criteriaBuilder.like(root.get("surname"), text.trim() + "%"),
                        criteriaBuilder.like(root.get("patronymic"), text.trim() + "%"));
    }

}
