package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.other.City;

import java.util.List;

public interface CityService {
    City save(City city);

    List<City> getAll();

    List<City> getAllByRegDistrictId(Long regDistrictId);

    City getById(Long id);
}
