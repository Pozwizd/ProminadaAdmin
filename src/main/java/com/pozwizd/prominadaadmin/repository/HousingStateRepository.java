package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.property.HousingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HousingStateRepository extends JpaRepository<HousingState, Long>, JpaSpecificationExecutor<HousingState> {
}