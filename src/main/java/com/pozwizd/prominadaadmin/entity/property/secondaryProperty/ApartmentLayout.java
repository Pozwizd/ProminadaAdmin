package com.pozwizd.prominadaadmin.entity.property.secondaryProperty;

import lombok.Getter;

@Getter
public enum ApartmentLayout {
    // Раздельная
    SEPARATE("Раздельная"),
    // Совмещенная
    COMBINED("Совмещенная");
    private final String name;
    ApartmentLayout(String name) {
        this.name = name;
    }

}
