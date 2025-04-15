package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.other.Topozone;

import java.util.List;

public interface TopozoneService {
    Topozone save(Topozone topozone);

    List<Topozone> getAll();

    List<Topozone> getAllByDistrictId(Long districtId);

    Topozone getById(Long topozoneId);
}
