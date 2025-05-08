package com.pozwizd.prominadaadmin.models.builderProperty;

import com.pozwizd.prominadaadmin.models.media.MediaDtoDrop;
import com.pozwizd.prominadaadmin.validator.annotation.MediaValidation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class BuilderPropertyDto {
    private Long id;
    @NotBlank(message = "error.field.empty")
    @Length(max = 30, message = "error.field.valid.length.title")
    private String name;
    @NotBlank(message = "error.field.empty")
    @Length(max = 50, message = "error.field.valid.length.title")
    private String street;
    @Min(value = 0, message = "error.field.valid.min.value")
    private Integer totalFloor;
    private String pathToChessPlanFile;
    private String pathToMortgageConditionsFile;
    private String pathToPriceFile;
    @MediaValidation(message = "error.file.valid", allowedTypes = {"image/png", "image/jpg", "image/jpeg"})
    private MultipartFile chessPlanFile;
    @MediaValidation(message = "error.file.valid", allowedTypes = {"image/png", "image/jpg", "image/jpeg"})
    private MultipartFile mortgageConditionsFile;
    @MediaValidation(message = "error.file.valid", allowedTypes = {"image/png", "image/jpg", "image/jpeg"})
    private MultipartFile priceFile;
    private String cityId;
    private String districtId;
    private String regDistrictId;
    private String topozoneId;
    private String buildingCompanyId;
    private String deliveryDateId;
    @Min(value = 0, message = "error.field.valid.min.value")
    private Integer houseNumber;
    @Min(value = 0, message = "error.field.valid.min.value")
    private Integer houseSection;
    private String phoneNumber;
    @Length(max = 300, message = "error.field.valid.length.title")
    private String description;
    @Length(max = 300, message = "error.field.valid.length.title")
    private String actionDescription;
    @Length(max = 50, message = "error.field.valid.length.title")
    private String actionTitle;
    private Boolean isAction;
    private List<MediaDtoDrop> filesDto;
    private List<BuilderPropertyLayoutDto> layoutDto;
}
