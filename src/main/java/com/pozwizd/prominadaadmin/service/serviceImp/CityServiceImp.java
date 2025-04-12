package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.repository.CityRepository;
import com.pozwizd.prominadaadmin.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImp implements CityService {
    private final CityRepository cityRepository;

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }
}
