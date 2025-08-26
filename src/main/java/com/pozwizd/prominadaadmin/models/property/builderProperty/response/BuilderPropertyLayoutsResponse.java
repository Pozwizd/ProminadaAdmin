package com.pozwizd.prominadaadmin.models.property.builderProperty.response;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * Response for {@link com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyLayouts}
 */
@Data
public class BuilderPropertyLayoutsResponse implements Serializable {
    Long id;
    String name;
    double priceByM2;
    Integer rooms;
    Double totalArea;
    Double livingArea;
    Double kitchenArea;
    Boolean visibleForSite;
    String nameFile1;
    String nameFile2;
    String nameFile3;
    String pathImage1;
    String pathImage2;
    String pathImage3;
    String description;
}