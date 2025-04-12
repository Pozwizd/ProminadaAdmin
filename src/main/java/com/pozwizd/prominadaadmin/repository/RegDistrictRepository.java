package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegDistrictRepository extends JpaRepository<RegDistrict, Long> {
}
