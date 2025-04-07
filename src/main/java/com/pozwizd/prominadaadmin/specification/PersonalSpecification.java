package com.pozwizd.prominadaadmin.specification;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Role;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public interface PersonalSpecification {

    static Specification<Personal> search(String surname, String name, String lastName, String phoneNumber, String email, String role) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(surname)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), "%" + surname.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(name)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(lastName)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
            }
            if (StringUtils.hasText(phoneNumber)) {
                predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%"));
            }
            if (StringUtils.hasText(email)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }
            if (role != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("role")), "%" + role.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
