package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SecondaryPropertyRepository extends JpaRepository<SecondaryProperty, Long>, JpaSpecificationExecutor<SecondaryProperty> {
}