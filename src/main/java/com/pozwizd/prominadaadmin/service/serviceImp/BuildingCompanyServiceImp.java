package com.pozwizd.prominadaadmin.service.serviceImp;

import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;
import com.pozwizd.prominadaadmin.repository.BuildingCompanyRepository;
import com.pozwizd.prominadaadmin.service.BuildingCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingCompanyServiceImp implements BuildingCompanyService {
    private final BuildingCompanyRepository buildingCompanyRepository;

    @Override
    public BuildingCompany save(BuildingCompany buildingCompany) {
        return buildingCompanyRepository.save(buildingCompany);
    }

    @Override
    public List<BuildingCompany> getAll() {
        return buildingCompanyRepository.findAll();
    }
}
