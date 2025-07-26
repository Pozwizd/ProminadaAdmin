package com.pozwizd.prominadaadmin.specification;

import com.pozwizd.prominadaadmin.entity.Realtor;
import com.pozwizd.prominadaadmin.filter.RealtorFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public interface RealtorSpecification {

    static Specification<Realtor> filterBy(RealtorFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filter.getCode()));
            }
            if (filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }
            if (filter.getSurname() != null && !filter.getSurname().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), "%" + filter.getSurname().toLowerCase() + "%"));
            }
            if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + filter.getLastName().toLowerCase() + "%"));
            }
            if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
            }
            if (filter.getBirthday() != null) {
                predicates.add(criteriaBuilder.equal(root.get("birthday"), filter.getBirthday()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

