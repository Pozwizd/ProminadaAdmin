package com.pozwizd.prominadaadmin.repository.secondary;

import com.pozwizd.prominadaadmin.entity.location.District;
import com.pozwizd.prominadaadmin.entity.location.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StreetRepository extends JpaRepository<Street, Long>, JpaSpecificationExecutor<Street> {
    Optional<Street> findByNameAndDistrict(String streetName, District district);
}