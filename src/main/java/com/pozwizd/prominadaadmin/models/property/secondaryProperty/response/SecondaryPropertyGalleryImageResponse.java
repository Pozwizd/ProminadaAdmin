package com.pozwizd.prominadaadmin.models.property.secondaryProperty.response;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyGalleryImage;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * Response for {@link SecondaryPropertyGalleryImage}
 */
@Data
public class SecondaryPropertyGalleryImageResponse implements Serializable {
    Long id;
    String name;
    String pathImage;
}