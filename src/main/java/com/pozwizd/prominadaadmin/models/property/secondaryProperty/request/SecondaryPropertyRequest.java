package com.pozwizd.prominadaadmin.models.property.secondaryProperty.request;

import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Request for {@link SecondaryProperty}
 */
@Data
public class SecondaryPropertyRequest implements Serializable {
    Long id;
    String street;
    Long cityId;
    Long regDistrictId;
    Long districtId;
    Long topozoneId;
    String houseNumber;
    String houseSection;
    String flatNumber;
    String ownerFullName;
    String phoneNumber;
    LocalDate acquisitionDate;
    OwnershipDoc ownershipDoc;
    String importantComment;
    List<SecondaryPropertyFileRequest> files;
    String adminComment;
    Long realtorId;
    SecondaryPropertyMainRequest secondaryPropertyMain;
    List<SecondaryPropertyGalleryImageRequest> galleryImages;
    LocalDate dateOfCreating;
}