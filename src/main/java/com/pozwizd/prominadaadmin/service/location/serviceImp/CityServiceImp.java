package com.pozwizd.prominadaadmin.service.location.serviceImp;

import com.pozwizd.prominadaadmin.entity.location.City;
import com.pozwizd.prominadaadmin.entity.location.District;
import com.pozwizd.prominadaadmin.entity.location.Region;
import com.pozwizd.prominadaadmin.repository.secondary.CityRepository;
import com.pozwizd.prominadaadmin.repository.secondary.RegionRepository;
import com.pozwizd.prominadaadmin.service.location.CityService;
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
public class CityServiceImp implements CityService {
    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;


    @Override
    public City getByNameAndRegionOrCreate(String cityName, Region region, String postalCode) {
        return cityRepository.findByNameAndRegion(cityName, region)
                .orElseGet(()
                        -> cityRepository.save(City.builder()
                        .name(cityName)
                        .region(region)
                        .postalCode(postalCode)
                        .build()));
    }

    @Override
    public List<City> getPageableCities(String name, Long regionId) {
        return cityRepository.findAll(
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    if (name != null && !name.trim().isEmpty()) {
                        predicates.add(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("name")),
                                        "%" + name.toLowerCase().trim() + "%"
                                )
                        );
                    }

                    if (regionId != null) {
                        Join<City, Region> districtJoin = root.join("region", JoinType.INNER);
                        predicates.add(
                                criteriaBuilder.equal(districtJoin.get("id"), regionId)
                        );
                    }

                    if (predicates.isEmpty()) {
                        return criteriaBuilder.conjunction();
                    }

                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }
        );
    }


    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }


    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> getAllByRegDistrictId(Long regDistrictId) {
        if (regDistrictId == null) return null;
        return cityRepository.findByRegion(regionRepository.findById(regDistrictId).orElseThrow());
    }

    @Override
    public City getById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City with id:" + id + " was not found!"));
    }


}
