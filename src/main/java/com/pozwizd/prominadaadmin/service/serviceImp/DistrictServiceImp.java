package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.other.District;
import com.pozwizd.prominadaadmin.repository.DistrictRepository;
import com.pozwizd.prominadaadmin.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictServiceImp implements DistrictService {
    private final DistrictRepository districtRepository;

    @Override
    public District save(District district) {
        return districtRepository.save(district);
    }

    @Override
    public List<District> getAll() {
        return districtRepository.findAll();
    }
}
