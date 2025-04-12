package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.other.Topozone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopozoneRepository extends JpaRepository<Topozone, Long> {
}
