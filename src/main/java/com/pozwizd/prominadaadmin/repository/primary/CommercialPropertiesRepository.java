package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommercialPropertiesRepository extends JpaRepository<CommercialProperties, Long>, JpaSpecificationExecutor<CommercialProperties> {



    @Query("SELECT cp FROM CommercialProperties cp " +
            "LEFT JOIN FETCH cp.commercialPropertiesMain " +
            "LEFT JOIN FETCH cp.region " +
            "LEFT JOIN FETCH cp.city " +
            "WHERE cp.id = :id")
    Optional<CommercialProperties> findByIdWithDetails(@Param("id") Long id);
}
