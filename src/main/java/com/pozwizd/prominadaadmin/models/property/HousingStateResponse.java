package com.pozwizd.prominadaadmin.models.property;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.pozwizd.prominadaadmin.entity.property.HousingState}
 */
@Value
public class HousingStateResponse implements Serializable {
    Long id;
    String name;
    String description;
}