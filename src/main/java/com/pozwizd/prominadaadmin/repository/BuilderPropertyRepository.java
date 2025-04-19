package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BuilderPropertyRepository extends JpaRepository<BuilderProperty, Long>, JpaSpecificationExecutor<BuilderProperty> {
}
