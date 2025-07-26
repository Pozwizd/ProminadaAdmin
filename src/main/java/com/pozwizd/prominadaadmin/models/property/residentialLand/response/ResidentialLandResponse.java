package com.pozwizd.prominadaadmin.models.property.residentialLand.response;

import com.pozwizd.prominadaadmin.entity.property.residentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * ResidentialLandResponse for {@link ResidentialLand}
 */
@Data
public class ResidentialLandResponse implements Serializable {

    Long id;
    Long regionId;
    Long cityId;
    Long districtId;
    Long streetId;
    Long houseId;
    Long topozoneId;
    String ownerFullName;
    String phoneNumber;
    LocalDate acquisitionDate;
    OwnershipDoc ownershipDoc;
    String importantComment;
    String cadastralNumber;
    String langPurpose;
    List<ResidentialLandFileResponse> files;
    String adminComment;
    ResidentialLandMainResponse ResidentialLandMain;
    List<ResidentialLandGalleryImageResponse> galleryImages;
    LocalDate dateOfCreating;
    Long realtorId;
}