package com.pozwizd.prominadaadmin.models.builderProperty;

import com.pozwizd.prominadaadmin.validator.annotation.MediaValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class BuilderPropertyLayoutDto {
    private Long id;
    @NotBlank(message = "layout.name.required")
    @Length(max = 30, message = "layout.name.length")
    private String name;
    @NotNull(message = "layout.priceByM2.required")
    @Min(value = 0, message = "layout.priceByM2.min")
    @Max(value = 100000, message = "layout.rooms.max")
    private Double priceByM2;

    @NotNull(message = "layout.rooms.required")
    @Min(value = 0, message = "layout.rooms.min")
    @Max(value = 10, message = "layout.rooms.max")
    private Integer rooms;

    @NotNull(message = "layout.totalArea.required")
    @Min(value = 0, message = "layout.totalArea.min")
    @Max(value = 1000, message = "layout.totalArea.max")
    private Double totalArea;

    @NotNull(message = "layout.livingArea.required")
    @Min(value = 0, message = "layout.livingArea.min")
    @Max(value = 500, message = "layout.livingArea.max")
    private Double livingArea;

    @NotNull(message = "layout.kitchenArea.required")
    @Min(value = 0, message = "layout.kitchenArea.min")
    @Max(value = 100, message = "layout.kitchenArea.max")
    private Double kitchenArea;

    private Boolean visibleForSite;

    @MediaValidation(message = "file.valid", allowedTypes = {
            "image/jpeg",
            "image/png",
            "image/webp"
    })
    private MultipartFile file1;
    private String fileName1;

    @MediaValidation(message = "file.valid", allowedTypes = {
            "image/jpeg",
            "image/png",
            "image/webp"
    })
    private MultipartFile file2;
    private String fileName2;

    @MediaValidation(message = "file.valid", allowedTypes = {
            "image/jpeg",
            "image/png",
            "image/webp"
    })
    private MultipartFile file3;
    private String fileName3;

    private String pathImage1;

    private String pathImage2;

    private String pathImage3;

    @Length(max = 300, message = "layout.description.length")
    private String description;
}
