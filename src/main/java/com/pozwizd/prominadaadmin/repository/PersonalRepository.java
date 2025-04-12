package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Long>, JpaSpecificationExecutor<Personal> {
    Optional<Personal> findByEmail(String email);

}