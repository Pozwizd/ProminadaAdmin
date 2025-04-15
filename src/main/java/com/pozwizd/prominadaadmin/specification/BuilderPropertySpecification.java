package com.pozwizd.prominadaadmin.specification;

import com.pozwizd.prominadaadmin.entity.Personal;
import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.models.builderProperty.BuilderPropertyDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public interface BuilderPropertySpecification {
    static Specification<BuilderProperty> search(BuilderPropertyDto dto) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(dto.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + dto.getName().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(dto.getNameTopozone())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("topozone").get("name")), "%" + dto.getNameTopozone().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(dto.getNameDistinct())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("distinct").get("name")), "%" + dto.getNameDistinct().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(dto.getStreet())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("street")), "%" + dto.getStreet().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(dto.getTotalFloor())) {
                predicates.add(criteriaBuilder.like(root.get("totalFloor"), "%" + dto.getTotalFloor() + "%"));
            }
//            if (StringUtils.hasText(dto.getPriceFrom())) {
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo((root.get("builderPropertyLayouts").get("priceByM2")), Double.parseDouble(dto.getPriceFrom())));
//            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
