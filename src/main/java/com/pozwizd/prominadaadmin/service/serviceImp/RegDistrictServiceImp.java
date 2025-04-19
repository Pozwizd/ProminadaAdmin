package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import com.pozwizd.prominadaadmin.repository.RegDistrictRepository;
import com.pozwizd.prominadaadmin.service.RegDistrictService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegDistrictServiceImp implements RegDistrictService {
    private final RegDistrictRepository regDistrictRepository;

    @Override
    public RegDistrict save(RegDistrict regDistrict) {
        return regDistrictRepository.save(regDistrict);
    }

    @Override
    public List<RegDistrict> getAll() {
        return regDistrictRepository.findAll();
    }

    @Override
    public RegDistrict getById(Long id) {
        return regDistrictRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RegDistrict with id: " + id + " was not found!"));
    }
}
