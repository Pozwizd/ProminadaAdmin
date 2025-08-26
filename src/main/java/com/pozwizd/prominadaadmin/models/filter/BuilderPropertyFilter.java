package com.pozwizd.prominadaadmin.models.filter;

import lombok.Data;

/**
 * Filter for {@link com.pozwizd.prominadaadmin.entity.property.builderProperty.BuilderProperty}
 */
@Data
public class BuilderPropertyFilter {
    private Integer page = 0;
    private Integer size = 10;
    private String name;
    private String cityId;
    private String districtId;
    private String streetId;
    private String topozoneId;
    private Integer floorFrom;
    private Integer priceFrom;
}
