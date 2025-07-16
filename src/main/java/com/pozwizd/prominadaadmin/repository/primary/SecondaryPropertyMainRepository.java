package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SecondaryPropertyMainRepository extends JpaRepository<SecondaryPropertyMain, Long>, JpaSpecificationExecutor<SecondaryPropertyMain> {
}