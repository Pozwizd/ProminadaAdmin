package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum ProjectHouse {

    SPECIAL_PROJECT("Спецпроект"),
    PRIVATE_HOUSE("Частный дом"),
    COMMERCIAL_PROPERTY("Коммерческая недвижимость");

    private String name;

    ProjectHouse(String name) {
        this.name = name;
    }
}
