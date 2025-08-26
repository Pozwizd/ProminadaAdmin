package com.pozwizd.prominadaadmin.models.property.investor.response;

import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Response for {@link com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty}
 */
@Data
public class InvestorPropertyResponse implements Serializable {
    Long id;
    InvestorPropertyMainResponse investorPropertyMain;
    List<InvestorPropertyFileResponse> investorPropertyFiles;
    List<InvestorPropertyGalleryImageResponse> investorPropertyGalleryImages;
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
    LocalDate dateOfCreating;
}