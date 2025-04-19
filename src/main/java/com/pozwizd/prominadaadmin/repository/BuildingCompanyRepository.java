package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.property.BuildingCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingCompanyRepository extends JpaRepository<BuildingCompany, Long> {
}
