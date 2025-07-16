package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long>, JpaSpecificationExecutor<Personal> {

    Optional<Personal> findByEmail(String email);

}