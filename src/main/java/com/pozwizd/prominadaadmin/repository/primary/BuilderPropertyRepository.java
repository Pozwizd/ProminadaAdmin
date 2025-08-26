package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuilderPropertyRepository extends JpaRepository<BuilderProperty, Long>, JpaSpecificationExecutor<BuilderProperty> {

    @Query("SELECT bp FROM BuilderProperty bp " +
            "LEFT JOIN FETCH bp.region " +
            "LEFT JOIN FETCH bp.city " +
            "WHERE bp.id = :id")
    Optional<BuilderProperty> findByIdWithDetails(Long id);

}
