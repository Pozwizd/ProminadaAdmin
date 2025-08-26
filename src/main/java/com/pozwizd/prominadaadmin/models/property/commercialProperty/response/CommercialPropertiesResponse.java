package com.pozwizd.prominadaadmin.models.property.commercialProperty.response;

import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialProperties;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Response for {@link CommercialProperties}
 */
@Data
public class CommercialPropertiesResponse implements Serializable {
    Long id;
    CommercialPropertiesMainResponse commercialPropertiesMain;
    List<CommercialPropertiesFileResponse> commercialPropertiesFiles;
    List<CommercialPropertiesGalleryImageResponse> commercialPropertiesGalleryImages;
    Long realtorId;
    Long regionId;
    Long cityId;
    Long districtId;
    Long streetId;
    Long houseId;
    Long topozoneId;
    String houseSection;
    String flatNumber;
    String ownerName;
    String phoneNumber;
    LocalDate acquisitionDate;
    OwnershipDoc ownershipDoc;
    String comment;
    String cadastralNumber;
    String langPurpose;
    String adminComment;
    LocalDate dateOfCreating;
}