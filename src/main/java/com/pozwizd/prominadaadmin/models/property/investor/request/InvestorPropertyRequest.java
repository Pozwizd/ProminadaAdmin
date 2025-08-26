package com.pozwizd.prominadaadmin.models.property.investor.request;

import com.pozwizd.prominadaadmin.entity.property.enums.OwnershipDoc;
import jakarta.validation.Valid;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Request for {@link com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty}
 */
@Data

public class InvestorPropertyRequest implements Serializable {
    Long id;
    @Valid
    InvestorPropertyMainRequest investorPropertyMainRequest;
    List<InvestorPropertyFileRequest> investorPropertyFileRequests;
    List<InvestorPropertyGalleryImageRequest> investorPropertyGalleryImageRequests;
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