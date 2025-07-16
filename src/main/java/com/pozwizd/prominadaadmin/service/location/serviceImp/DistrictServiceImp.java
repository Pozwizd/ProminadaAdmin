package com.pozwizd.prominadaadmin.service.location.serviceImp;

import com.pozwizd.prominadaadmin.entity.location.City;
import com.pozwizd.prominadaadmin.entity.location.District;
import com.pozwizd.prominadaadmin.entity.location.Region;
import com.pozwizd.prominadaadmin.repository.secondary.DistrictRepository;
import com.pozwizd.prominadaadmin.service.location.CityService;
import com.pozwizd.prominadaadmin.service.location.DistrictService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DistrictServiceImp implements DistrictService {
    private final DistrictRepository districtRepository;
    private final CityService cityService;

    @Override
    public District save(District district) {
        return districtRepository.save(district);
    }

    @Override
    public List<District> getAll() {
        return districtRepository.findAll();
    }


    @Override
    public District getById(Long id) {
        return districtRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(""));
    }

    @Override
    public List<District> getPageableDistricts(String district, Long cityId) {
        return districtRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (district != null && !district.trim().isEmpty()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + district.toLowerCase().trim() + "%"
                        )
                );
            }

            if (cityId != null) {
                Join<City, Region> districtJoin = root.join("city", JoinType.INNER);
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