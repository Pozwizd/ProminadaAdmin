package com.pozwizd.prominadaadmin.models.builderProperty;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class BuilderPropertyLayoutDto {
    private Long id;

    private String name;

    private Double priceByM2;

    private Integer rooms;

    private Double totalArea;

    private Double livingArea;

    private Double kitchenArea;

    private Boolean visibleForSite;

    private MultipartFile file1;
    private String fileName1;

    private MultipartFile file2;
    private String fileName2;

    private MultipartFile file3;
    private String fileName3;

    private String pathImage1;

    private String pathImage2;

    private String pathImage3;

    private String description;
}
