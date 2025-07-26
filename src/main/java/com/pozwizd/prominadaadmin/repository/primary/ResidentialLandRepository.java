package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidentialLandRepository extends JpaRepository<ResidentialLand, Long>, JpaSpecificationExecutor<ResidentialLand> {

    @Query("SELECT rl FROM ResidentialLand rl " +
            "LEFT JOIN FETCH rl.residentialLandMain " +
            "LEFT JOIN FETCH rl.region " +
            "LEFT JOIN FETCH rl.city " +
            "WHERE rl.id = :id")
    Optional<ResidentialLand> findByIdWithDetails(@Param("id") Long id);
}