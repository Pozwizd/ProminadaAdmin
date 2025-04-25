package com.pozwizd.prominadaadmin.models.builderProperty;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class BuilderPropertyDtoForTable {
    private Long id;
    private String name;
    private String street;
    private String totalFloor;
    private String nameDistinct;
    private String nameTopozone;
}
