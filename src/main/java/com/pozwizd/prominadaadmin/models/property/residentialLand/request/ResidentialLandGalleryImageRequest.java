package com.pozwizd.prominadaadmin.models.property.residentialLand.request;

import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLandGalleryImage;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Request for {@link ResidentialLandGalleryImage}
 */
@Data
public class ResidentialLandGalleryImageRequest implements Serializable {
    Long id;
    String name;
    MultipartFile file; // Changed from pathImage to file
}