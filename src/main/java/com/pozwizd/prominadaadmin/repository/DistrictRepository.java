package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.other.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
}