package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.Realtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RealtorRepository extends JpaRepository<Realtor, Long>, JpaSpecificationExecutor<Realtor> {
    Optional<Realtor> findByEmail(String email);
}
