package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResidentialLandRepository extends JpaRepository<ResidentialLand, Long>, JpaSpecificationExecutor<ResidentialLand> {
}