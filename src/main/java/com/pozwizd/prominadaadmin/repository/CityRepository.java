package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.other.City;
import com.pozwizd.prominadaadmin.entity.other.RegDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> getCitiesByRegDistrict(RegDistrict regDistrict);
}
