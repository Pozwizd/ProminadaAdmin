package com.pozwizd.prominadaadmin.models.property.residentialLand.request;

import com.pozwizd.prominadaadmin.entity.property.ResidentialLand.ResidentialLand;
import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Request for {@link ResidentialLand}
 */
@Data
public class ResidentialLandRequest implements Serializable {
    Long id;
    Integer houseNumber;
    Long regionId;
    Long cityId;
    Long streetId;
    Long districtId;
    Long topozoneId;
    String ownerFullName;
    String phoneNumber;
    LocalDate acquisitionDate;
    OwnershipDoc ownershipDoc;
    String importantComment;
    String cadastralNumber;
    String langPurpose;
    List<ResidentialLandFileRequest> residentialLandFiles;
    String adminComment;
    ResidentialLandMainRequest residentialLandMain;
    List<ResidentialLandGalleryImageRequest> residentialLandGalleryImages;
    LocalDate dateOfCreating;
}