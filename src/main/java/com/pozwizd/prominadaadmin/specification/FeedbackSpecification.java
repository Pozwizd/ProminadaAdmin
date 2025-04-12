package com.pozwizd.prominadaadmin.specification;

import com.pozwizd.prominadaadmin.entity.Feedback;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public interface FeedbackSpecification {

    /**
     * Создает спецификацию для поиска отзывов по различным критериям.
     * 
     * @param name Имя клиента для фильтрации
     * @param phoneNumber Телефон для фильтрации
     * @param description Описание для фильтрации
     * @return Спецификация для фильтрации отзывов
     */
    static Specification<Feedback> search(String name, String phoneNumber, String description) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(name)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            
            if (StringUtils.hasText(phoneNumber)) {
                predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%"));
            }
            
            if (StringUtils.hasText(description)) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}