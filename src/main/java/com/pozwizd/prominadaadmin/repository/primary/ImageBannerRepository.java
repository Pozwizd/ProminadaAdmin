package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.ImageBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageBannerRepository extends JpaRepository<ImageBanner, Long>, JpaSpecificationExecutor<ImageBanner> {
    List<ImageBanner> findByBannerId(Long bannerId);
}