package com.pozwizd.prominadaadmin.models.property.residentialLand.response;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLandGalleryImage}
 */
@Value
public class ResidentialLandGalleryImageRequest implements Serializable {
    Long id;
    String name;
    String pathImage;
}