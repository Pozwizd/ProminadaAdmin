package com.pozwizd.prominadaadmin.models.property.builderProperty.request;

import com.pozwizd.prominadaadmin.entity.property.enums.DeliveryDate;
import com.pozwizd.prominadaadmin.models.property.builderProperty.response.BuilderPropertyGalleryImageResponse;
import com.pozwizd.prominadaadmin.models.property.builderProperty.response.BuilderPropertyLayoutsResponse;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Request for {@link com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty}
 */
@Data
public class BuilderPropertyRequest implements Serializable {
    Long id;
    List<BuilderPropertyGalleryImageRequest> builderPropertyGalleryImageRequests;
    List<BuilderPropertyLayoutsRequest> builderPropertyLayoutsRequests;
    Long buildingCompanyId;
    Long regionId;
    Long cityId;
    Long districtId;
    Long streetId;
    Long houseId;
    Long topozoneId;
    String name;
    Integer houseNumber;
    Integer houseSection;
    Integer totalFloor;
    DeliveryDate deliveryDate;
    String phoneNumber;
    MultipartFile pathToChessPlanFile;
    MultipartFile pathToMortgageConditionsFile;
    MultipartFile pathToPriceFile;
    String description;
    String actionTitle;
    String actionDescription;
    Boolean isAction;
    LocalDate dateOfCreating;
}