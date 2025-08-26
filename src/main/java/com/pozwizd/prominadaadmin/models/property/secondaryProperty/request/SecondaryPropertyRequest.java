package com.pozwizd.prominadaadmin.models.property.secondaryProperty.request;

import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryProperty;
import com.pozwizd.prominadaadmin.validator.branch.NotFoundBranch;
import jakarta.validation.Valid;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Request for {@link SecondaryProperty}
 */
@Data
@NotFoundBranch(
        message = "Филиал по branchCode не найден",
        branchCodeField = "branchCode",
        nullable = false
)
public class SecondaryPropertyRequest implements Serializable {

    @Valid
    SecondaryPropertyMainRequest secondaryPropertyMainRequest;
    List<SecondaryPropertyFileRequest> secondaryPropertyFileRequests;
    List<SecondaryPropertyGalleryImageRequest> secondaryPropertyGalleryImageRequests;

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
    String adminComment;
    Long realtorId;
    LocalDate dateOfCreating;
}