package com.pozwizd.prominadaadmin.repository.primary;

import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandGalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResidentialLandGalleryImageRepository extends JpaRepository<ResidentialLandGalleryImage, Long>, JpaSpecificationExecutor<ResidentialLandGalleryImage> {
}