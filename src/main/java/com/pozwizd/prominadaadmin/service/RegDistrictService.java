package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.other.RegDistrict;

import java.util.List;

public interface RegDistrictService {
    RegDistrict save(RegDistrict regDistrict);

    List<RegDistrict> getAll();

    RegDistrict getById(Long id);
}
