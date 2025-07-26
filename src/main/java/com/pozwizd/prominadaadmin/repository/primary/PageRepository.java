package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, Long>, JpaSpecificationExecutor<Page> {


}
