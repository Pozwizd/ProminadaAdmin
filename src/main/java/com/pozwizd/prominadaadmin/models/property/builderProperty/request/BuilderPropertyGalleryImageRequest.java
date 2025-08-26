package com.pozwizd.prominadaadmin.models.property.builderProperty.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Request for {@link com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyGalleryImage}
 */
@Data
public class BuilderPropertyGalleryImageRequest {
    Long id;
    String name;
    MultipartFile pathImage;
}
