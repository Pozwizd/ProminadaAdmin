package com.pozwizd.prominadaadmin.specification;

import com.pozwizd.prominadaadmin.entity.location.City;
import org.springframework.data.jpa.domain.Specification;

public interface CitySpecification {

    static Specification<City> search(String name) {

        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

}
