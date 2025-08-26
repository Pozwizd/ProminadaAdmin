package com.pozwizd.prominadaadmin.models.property.residentialLand.request;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Request for {@link ResidentialLand}
 */
@Data
@Builder
public class ResidentialLandRequest implements Serializable {
    Long id;

    @Valid
    ResidentialLandMainRequest residentialLandMain;
    List<ResidentialLandGalleryImageRequest> residentialLandGalleryImages;
    List<ResidentialLandFileRequest> residentialLandFiles;

    Long regionId;
    Long cityId;
    Long streetId;
    Long districtId;
    Long topozoneId;
    Long houseId;
    String ownerFullName;
    String phoneNumber;
    LocalDate acquisitionDate;
    OwnershipDoc ownershipDoc;
    String importantComment;
    String cadastralNumber;
    String langPurpose;
    String adminComment;
    LocalDate dateOfCreating;
}