package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long> {
    Optional<Personal> findByEmail(String email); // Needed for security
}