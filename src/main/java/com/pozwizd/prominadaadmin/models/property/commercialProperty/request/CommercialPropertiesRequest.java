package com.pozwizd.prominadaadmin.models.property.commercialProperty.request;

import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialProperties;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Response for {@link CommercialProperties}
 */
@Data
public class CommercialPropertiesRequest implements Serializable {
    Long id;
    CommercialPropertiesMainRequest commercialPropertiesMain;
    List<CommercialPropertiesFileRequest> commercialPropertiesFiles;
    List<CommercialPropertiesGalleryImageRequest> commercialPropertiesGalleryImages;
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