package com.pozwizd.prominadaadmin.models.property.secondaryProperty.response;

import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Response for {@link SecondaryProperty}
 */
@Data
public class SecondaryPropertyResponse implements Serializable {

    Long id;
    Long regionId;
    Long cityId;
    Long districtId;
    Long streetId;
    Long houseId;
    Long topozoneId;

    String houseSection;
    String flatNumber;
    String ownerFullName;
    String phoneNumber;
    LocalDate acquisitionDate;
    OwnershipDoc ownershipDoc;
    String importantComment;
    List<SecondaryPropertyFileResponse> files;
    String adminComment;
    Long realtorId;
    SecondaryPropertyMainResponse secondaryPropertyMain;
    List<SecondaryPropertyGalleryImageResponse> galleryImages;
    LocalDate dateOfCreating;
}