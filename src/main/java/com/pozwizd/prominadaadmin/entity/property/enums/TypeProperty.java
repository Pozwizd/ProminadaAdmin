package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum TypeProperty {

    LAND("Земля"),
    HOUSE("Дом"),
    LAND_AND_HOUSE("Земля и дом");
    private final String name;
    TypeProperty(String name) {
        this.name = name;
    }
}
