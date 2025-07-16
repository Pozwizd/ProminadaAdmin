package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResidentialLandMainRepository extends JpaRepository<ResidentialLandMain, Long>, JpaSpecificationExecutor<ResidentialLandMain> {
}