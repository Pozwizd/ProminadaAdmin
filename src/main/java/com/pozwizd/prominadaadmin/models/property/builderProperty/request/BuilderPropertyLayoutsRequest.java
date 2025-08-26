package com.pozwizd.prominadaadmin.models.property.builderProperty.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Response for {@link com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderPropertyLayouts}
 */

@Data
public class BuilderPropertyLayoutsRequest {
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
    MultipartFile pathImage1;
    MultipartFile pathImage2;
    MultipartFile pathImage3;
    String description;
}
