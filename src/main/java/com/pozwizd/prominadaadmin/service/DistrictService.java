package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.other.District;

import java.util.List;

public interface DistrictService {
    District save(District district);

    List<District> getAll();
}
