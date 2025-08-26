package com.pozwizd.prominadaadmin.models.property.builderProperty.response;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * Response for {@link com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyGalleryImage}
 */
@Data
public class BuilderPropertyGalleryImageResponse implements Serializable {
    Long id;
    String name;
    String pathImage;
}