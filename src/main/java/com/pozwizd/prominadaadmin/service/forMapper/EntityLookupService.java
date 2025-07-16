package com.pozwizd.prominadaadmin.service.forMapper;

import com.pozwizd.prominadaadmin.entity.location.*;
import com.pozwizd.prominadaadmin.repository.secondary.*;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityLookupService {

    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final TopozoneRepository topozoneRepository;
    private final StreetRepository streetRepository;
    private final HouseRepository houseRepository;


    @Named("findDistrictById")
    public District findDistrictById(Long id) {
        return id != null ? districtRepository.findById(id).orElse(null) : null;
    }

    @Named("findCityById")
    public City findCityById(Long id) {
        return id != null ? cityRepository.findById(id).orElse(null) : null;
    }

    @Named("findRegionById")
    public Region findRegionById(Long id) {
        return id != null ? regionRepository.findById(id).orElse(null) : null;
    }

    @Named("findTopozoneById")
    public Topozone findTopozoneById(Long id) {
        return id != null ? topozoneRepository.findById(id).orElse(null) : null;
    }

    @Named("findHouseById")
    public House findHouseById(Long id) {
        return id != null ? houseRepository.findById(id).orElse(null) : null;
    }

    @Named("findStreetById")
    public Street findStreetById(Long id) {
        return id != null ? streetRepository.findById(id).orElse(null) : null;
    }
}
