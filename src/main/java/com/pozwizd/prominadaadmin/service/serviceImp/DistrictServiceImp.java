package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.other.District;
import com.pozwizd.prominadaadmin.repository.DistrictRepository;
import com.pozwizd.prominadaadmin.service.CityService;
import com.pozwizd.prominadaadmin.service.DistrictService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<District> getAllByCityId(Long cityId) {
        return districtRepository.getAllByCity(cityService.getById(cityId));
    }

    @Override
    public District getById(Long id) {
        return districtRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(""));
    }
}