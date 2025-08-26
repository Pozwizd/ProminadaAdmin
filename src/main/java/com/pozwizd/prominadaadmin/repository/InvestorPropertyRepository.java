package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvestorPropertyRepository extends JpaRepository<InvestorProperty, Long>, JpaSpecificationExecutor<InvestorProperty> {

    @Query("SELECT ip FROM InvestorProperty ip " +
            "LEFT JOIN FETCH ip.investorPropertyMain " +
            "LEFT JOIN FETCH ip.region " +
            "LEFT JOIN FETCH ip.city " +
            "WHERE ip.id = :id")
    Optional<InvestorProperty> findByIdWithDetails(Long id);

}