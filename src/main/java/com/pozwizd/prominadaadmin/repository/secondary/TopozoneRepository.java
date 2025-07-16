package com.pozwizd.prominadaadmin.repository.secondary;

import com.pozwizd.prominadaadmin.entity.location.Topozone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopozoneRepository extends JpaRepository<Topozone, Long> {
}
