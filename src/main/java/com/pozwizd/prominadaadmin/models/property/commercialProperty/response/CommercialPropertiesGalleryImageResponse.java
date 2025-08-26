package com.pozwizd.prominadaadmin.models.property.commercialProperty.response;

import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesGalleryImage;
import lombok.Data;

import java.io.Serializable;

/**
 * Response for {@link CommercialPropertiesGalleryImage}
 */
@Data
public class CommercialPropertiesGalleryImageResponse implements Serializable {
    Long id;
    String name;
    String pathImage;
}