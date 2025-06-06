package com.pozwizd.prominadaadmin.specification;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.Realtor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public interface RealtorSpecification {
    static Specification<Realtor> search(String id, String code, String fullname, String email, String dateOfBirthday) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(fullname)) {
                String[] parts = fullname.trim().split("\\s+");
                if (parts.length > 0) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + parts[2].toLowerCase() + "%"));
                }
                if (parts.length > 1) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + parts[1].toLowerCase() + "%"));
                }
                if (parts.length > 2) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), "%" + parts[0].toLowerCase() + "%"));
                }
            }

            if (StringUtils.hasText(email)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(id)) {
                predicates.add(criteriaBuilder.equal(root.get("id"), Integer.valueOf(id)));
            }

            if (StringUtils.hasText(code)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%" + code.toLowerCase() + "%"));
            }


            if (StringUtils.hasText(dateOfBirthday)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate parsedDate = LocalDate.parse(dateOfBirthday, formatter);
                predicates.add(dateOfBirthdayBetween(parsedDate).toPredicate(root, query, criteriaBuilder));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Specification<Realtor> dateOfBirthdayBetween(LocalDate date) {
        return (root, query, cb) -> {
//            LocalDateTime startOfDay = date.atStartOfDay();
//            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            return cb.equal(root.get("dateOfBirthday"), date);
        };
    }
}
