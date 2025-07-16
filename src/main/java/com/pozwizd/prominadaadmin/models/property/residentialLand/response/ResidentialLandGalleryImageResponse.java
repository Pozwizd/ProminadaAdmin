package com.pozwizd.prominadaadmin.models.property.residentialLand.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResidentialLandGalleryImageResponse implements Serializable {
    Long id;
    String name;
    String pathImage;
}