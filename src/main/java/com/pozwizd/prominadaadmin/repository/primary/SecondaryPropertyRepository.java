package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SecondaryPropertyRepository extends JpaRepository<SecondaryProperty, Long>, JpaSpecificationExecutor<SecondaryProperty> {

    @Query("SELECT sp FROM SecondaryProperty sp " +
            "LEFT JOIN FETCH sp.secondaryPropertyMain " +
            "LEFT JOIN FETCH sp.region " +
            "LEFT JOIN FETCH sp.city " +
            "WHERE sp.id = :id")
    Optional<SecondaryProperty> findByIdWithDetails(@Param("id") Long id);

}