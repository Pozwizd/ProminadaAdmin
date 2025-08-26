package com.pozwizd.prominadaadmin.specification;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import com.pozwizd.prominadaadmin.models.filter.BuilderPropertyFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public interface BuilderPropertySpecification {
    static Specification<BuilderProperty> search(BuilderPropertyFilter builderPropertyFilter) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(builderPropertyFilter.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(
                        root.get("name")), "%" + builderPropertyFilter.getName().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(builderPropertyFilter.getCityId())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(
                        root.get("city").get("id")), "%" + builderPropertyFilter.getCityId().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(builderPropertyFilter.getDistrictId())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(
                        root.get("district").get("id")), "%" + builderPropertyFilter.getDistrictId().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(builderPropertyFilter.getStreetId())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(
                        root.get("street").get("id")), "%" + builderPropertyFilter.getStreetId().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
