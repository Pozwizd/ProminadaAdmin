package com.pozwizd.prominadaadmin.repository.secondary;

import com.pozwizd.prominadaadmin.entity.location.City;
import com.pozwizd.prominadaadmin.entity.location.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {
    List<City> findByRegion(Region region);

    // ПРАВИЛЬНО
    // или
    List<City> findAllByRegion(Region region);
    Optional<City> findByNameAndDistrictsId(String name, Long districtId);

    Optional<City> findByNameAndRegion(String cityName, Region region);
}
