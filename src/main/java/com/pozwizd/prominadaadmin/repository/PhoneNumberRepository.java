package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long>, JpaSpecificationExecutor<PhoneNumber> {
}
