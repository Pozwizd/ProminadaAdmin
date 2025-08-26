package com.pozwizd.prominadaadmin.models.property.builderProperty.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuilderPropertyResponseForTable {
    private Long id;
    private String name;
    private String city;
    private String district;
    private String street;
    private String topozone;
    private String totalFloor;
    private String price;
}
