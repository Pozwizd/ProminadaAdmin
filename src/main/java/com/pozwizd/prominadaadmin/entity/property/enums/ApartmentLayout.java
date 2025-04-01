package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum ApartmentLayout {

    SEPARATE("Раздельная"),
    COMBINED("Совмещенная");
    private String name;

    ApartmentLayout(String name) {
        this.name = name;
    }
} 