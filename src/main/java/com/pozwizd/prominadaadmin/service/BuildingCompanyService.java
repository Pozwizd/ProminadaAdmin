package com.pozwizd.prominadaadmin.service;

import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;

import java.util.List;

public interface BuildingCompanyService {
    BuildingCompany save(BuildingCompany buildingCompany);

    List<BuildingCompany> getAll();
}
