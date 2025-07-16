package com.pozwizd.prominadaadmin.service.location;

import com.pozwizd.prominadaadmin.entity.location.City;
import com.pozwizd.prominadaadmin.entity.location.Region;

import java.util.List;

public interface CityService {

    List<City> getPageableCities(String name, Long regionId);

    List<City> getAllByRegDistrictId(Long regDistrictId);

    City getById(Long id);

    City getByNameAndRegionOrCreate(String cityName, Region region, String postalCode);

    List<City> getAll();

    City save(City city);

}
