package com.pozwizd.prominadaadmin.models.builderProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuilderPropertyDto {
    private Long id;
    private String name;
    private String nameDistinct;
    private String nameTopozone;
    private String street;
    private String totalFloor;
}
