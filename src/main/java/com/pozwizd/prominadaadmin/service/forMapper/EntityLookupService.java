package com.pozwizd.prominadaadmin.service.forMapper;

import com.pozwizd.prominadaadmin.entity.location.City;
import com.pozwizd.prominadaadmin.entity.location.District;
import com.pozwizd.prominadaadmin.entity.location.House;
import com.pozwizd.prominadaadmin.entity.location.Region;
import com.pozwizd.prominadaadmin.entity.location.Street;
import com.pozwizd.prominadaadmin.entity.location.Topozone;
import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;
import com.pozwizd.prominadaadmin.entity.property.HousingState;
import com.pozwizd.prominadaadmin.repository.HousingStateRepository;
import com.pozwizd.prominadaadmin.repository.primary.BuildingCompanyRepository;
import com.pozwizd.prominadaadmin.repository.secondary.CityRepository;
import com.pozwizd.prominadaadmin.repository.secondary.DistrictRepository;
import com.pozwizd.prominadaadmin.repository.secondary.HouseRepository;
import com.pozwizd.prominadaadmin.repository.secondary.RegionRepository;
import com.pozwizd.prominadaadmin.repository.secondary.StreetRepository;
import com.pozwizd.prominadaadmin.repository.secondary.TopozoneRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "lookup") // дефолтный кэш для всех методов этого сервиса
public class EntityLookupService {

    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final TopozoneRepository topozoneRepository;
    private final StreetRepository streetRepository;
    private final HouseRepository houseRepository;

    private final HousingStateRepository housingStateRepository;
    private final BuildingCompanyRepository buildingCompanyRepository;

    @Named("findBuildingCompanyById")
    @Cacheable(key = "'buildingCompany:' + #id")
    public BuildingCompany findBuildingCompanyById(Long id) {
        return id != null ? buildingCompanyRepository.findById(id).orElse(null) : null;
    }

    @Named("findDistrictById")
    @Cacheable(key = "'district:' + #id")
    public District findDistrictById(Long id) {
        return id != null ? districtRepository.findById(id).orElse(null) : null;
    }

    @Named("findCityById")
    @Cacheable(key = "'city:' + #id")
    public City findCityById(Long id) {
        return id != null ? cityRepository.findById(id).orElse(null) : null;
    }

    @Named("findRegionById")
    @Cacheable(key = "'region:' + #id")
    public Region findRegionById(Long id) {
        return id != null ? regionRepository.findById(id).orElse(null) : null;
    }

    @Named("findTopozoneById")
    @Cacheable(key = "'topozone:' + #id")
    public Topozone findTopozoneById(Long id) {
        return id != null ? topozoneRepository.findById(id).orElse(null) : null;
    }

    @Named("findHouseById")
    @Cacheable(key = "'house:' + #id")
    public House findHouseById(Long id) {
        return id != null ? houseRepository.findById(id).orElse(null) : null;
    }

    @Named("findStreetById")
    @Cacheable(key = "'street:' + #id")
    public Street findStreetById(Long id) {
        return id != null ? streetRepository.findById(id).orElse(null) : null;
    }

    @Named("findHousingStateById")
    @Cacheable(key = "'housingState:' + #id")
    public HousingState findHousingStateById(Long id) {
        return id != null ? housingStateRepository.findById(id).orElse(null) : null;
    }
}
