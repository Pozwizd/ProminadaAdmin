package com.pozwizd.prominadaadmin.repository.secondary;

import com.pozwizd.prominadaadmin.entity.location.House;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<House> findByNumberAndStreetId(String number, Long streetId);
}