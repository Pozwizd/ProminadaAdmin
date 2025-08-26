package com.pozwizd.prominadaadmin.models.property.residentialLand.response.table;

import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ResidentialLandTableResponse implements Serializable {
    Long id;
    Integer houseNumber;
    String street;
    String regDistrictName;
    String districtName;
    String topozoneName;
    String ownerFullName;
    String phoneNumber;
    LocalDate acquisitionDate;
    OwnershipDoc ownershipDoc;
    String importantComment;
    String cadastralNumber;
    String langPurpose;
    String adminComment;
    ResidentialLandMainTableResponse residentialLandMainTableResponse;
    LocalDate dateOfCreating;
    Boolean hasPhotos;
}