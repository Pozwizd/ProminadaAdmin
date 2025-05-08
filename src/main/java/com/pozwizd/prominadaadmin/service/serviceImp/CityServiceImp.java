package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import com.pozwizd.prominadaadmin.repository.CityRepository;
import com.pozwizd.prominadaadmin.service.CityService;
import com.pozwizd.prominadaadmin.service.RegDistrictService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImp implements CityService {
    private final CityRepository cityRepository;
    private final RegDistrictService regDistrictService;

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
        return cityRepository.getCitiesByRegDistrict(regDistrictService.getById(regDistrictId));
    }

    @Override
    public City getById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City with id:" + id + " was not found!"));
    }
}
