package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum TypeCommercialBuilding {

    HOUSE("Дом"),
    APARTMENT("Квартира"),
    OFFICE("Офис"),
    OTHER("Другое");

    private final String description;

    TypeCommercialBuilding(String description) {
        this.description = description;
    }

}
