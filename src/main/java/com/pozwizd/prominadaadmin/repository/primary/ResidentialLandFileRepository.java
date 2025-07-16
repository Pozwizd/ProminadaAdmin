package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResidentialLandFileRepository extends JpaRepository<ResidentialLandFile, Long>, JpaSpecificationExecutor<ResidentialLandFile> {
}