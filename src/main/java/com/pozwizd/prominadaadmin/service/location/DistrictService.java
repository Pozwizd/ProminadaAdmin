package com.pozwizd.prominadaadmin.service.location;

import com.pozwizd.prominadaadmin.entity.location.District;

import java.util.List;

public interface DistrictService {
    District save(District district);

    List<District> getAll();


    District getById(Long id);

    List<District> getPageableDistricts(String district, Long cityId);
}
