package com.pozwizd.prominadaadmin.models.property.commercialProperty.request;

import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialPropertiesGalleryImage;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Response for {@link CommercialPropertiesGalleryImage}
 */
@Data
public class CommercialPropertiesGalleryImageRequest implements Serializable {
    Long id;
    String name;
    MultipartFile pathImage;
}