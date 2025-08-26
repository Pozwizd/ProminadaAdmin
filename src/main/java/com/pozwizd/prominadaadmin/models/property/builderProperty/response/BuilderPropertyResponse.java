package com.pozwizd.prominadaadmin.models.property.builderProperty.response;

import com.pozwizd.prominadaadmin.entity.property.enums.DeliveryDate;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Response for {@link com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty}
 */
@Data
public class BuilderPropertyResponse implements Serializable {
    Long id;
    List<BuilderPropertyGalleryImageResponse> builderPropertyGalleryImages;
    List<BuilderPropertyLayoutsResponse> builderPropertyLayouts;
    Long buildingCompanyId;
    Long regionId;
    Long cityId;
    Long districtId;
    Long streetId;
    Long houseId;
    Long topozoneId;
    String name;
    Integer houseNumber;
    Integer houseSection;
    Integer totalFloor;
    DeliveryDate deliveryDate;
    String phoneNumber;
    String pathToChessPlanFile;
    String pathToMortgageConditionsFile;
    String pathToPriceFile;
    String description;
    String actionTitle;
    String actionDescription;
    Boolean isAction;
    LocalDate dateOfCreating;
}