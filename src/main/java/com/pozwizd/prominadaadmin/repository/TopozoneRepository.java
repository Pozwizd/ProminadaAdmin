package com.pozwizd.prominadaadmin.repository;

import com.pozwizd.prominadaadmin.entity.other.District;
import com.pozwizd.prominadaadmin.entity.other.Topozone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopozoneRepository extends JpaRepository<Topozone, Long> {
    List<Topozone> getAllByDistrict(District district);
}
