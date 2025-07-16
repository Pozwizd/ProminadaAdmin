package com.pozwizd.prominadaadmin.service.location.serviceImp;

import com.pozwizd.prominadaadmin.entity.location.City;
import com.pozwizd.prominadaadmin.entity.location.Region;
import com.pozwizd.prominadaadmin.entity.location.Street;
import com.pozwizd.prominadaadmin.repository.secondary.StreetRepository;
import com.pozwizd.prominadaadmin.service.location.StreetService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StreetServiceImpl implements StreetService {
    private final StreetRepository streetRepository;


    @Override
    public List<Street> getAll() {
        return streetRepository.findAll();
    }

    @Override
    public List<Street> getPageableStreet(String street, Long cityId) {
        return streetRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (street != null && !street.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + street.toLowerCase().trim() + "%"
                        )
                );
            }

            if (cityId != null) {
                Join<City, Region> districtJoin = root.join("district", JoinType.INNER);
                predicates.add(
                        criteriaBuilder.equal(districtJoin.get("id"), cityId)
                );
            }

            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });
    }
}