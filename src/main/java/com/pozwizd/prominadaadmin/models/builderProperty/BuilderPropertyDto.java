package com.pozwizd.prominadaadmin.models.builderProperty;

import com.pozwizd.prominadaadmin.models.media.MediaDtoDrop;
import com.pozwizd.prominadaadmin.validation.ConditionalActionValidation;
import com.pozwizd.prominadaadmin.validator.annotation.MediaValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@ConditionalActionValidation
public class BuilderPropertyDto {
    private Long id;
    private String code;
    private String dateOfBirthday;
    @NotBlank(message = "name.required")
    @Length(max = 30, message = "name.length")
    private String name;
    @NotBlank(message = "street.required")
    @Length(max = 50, message = "street.length")
    private String street;
    @NotNull(message = "totalFloor.required")
    @Min(value = 0, message = "totalFloor.min")
    @Max(value = 5, message = "totalFloor.max")
    private Integer totalFloor;
    private String pathToChessPlanFile;
    private String pathToMortgageConditionsFile;
    private String pathToPriceFile;
    @MediaValidation(message = "file.valid", allowedTypes = {
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/pdf"})
    private MultipartFile chessPlanFile;
    @MediaValidation(message = "file.valid", allowedTypes = {
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/pdf"})
    private MultipartFile mortgageConditionsFile;
    @MediaValidation(message = "file.valid", allowedTypes = {
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/pdf"})
    private MultipartFile priceFile;
    @NotBlank(message = "city.required")
    private String cityId;
    @NotBlank(message = "district.required")
    private String districtId;
    @NotBlank(message = "regDistrict.required")
    private String regDistrictId;
    @NotBlank(message = "topozone.required")
    private String topozoneId;
    @NotBlank(message = "buildingCompany.required")
    private String buildingCompanyId;
    @NotBlank(message = "deliveryType.required")
    private String deliveryType;
    @NotNull(message = "houseNumber.required")
    @Min(value = 0, message = "houseNumber.min")
    @Max(value = 10, message = "houseNumber.max")
    private Integer houseNumber;
    @NotNull(message = "houseSection.required")
    @Min(value = 0, message = "houseSection.min")
    @Max(value = 10, message = "houseSection.max")
    private Integer houseSection;
    @Pattern(regexp = "\\+380(50|66|95|99|67|68|96|97|98|63|93|73)[0-9]{7}", message = "phone.pattern")
    private String phoneNumber;
    @Length(max = 300, message = "description.length")
    private String description;
    private String actionTitle;
    private String actionDescription;
    private Boolean isAction;
    private List<MediaDtoDrop> filesDto;
    @Valid
    private List<BuilderPropertyLayoutDto> layoutDto;
}