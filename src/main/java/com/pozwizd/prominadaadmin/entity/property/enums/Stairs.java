package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum Stairs {

    Concrete("Бетонная"),
    Wood("Деревянная"),
    NoStairs("Нет");

    private final String description;

    Stairs(String description) {
        this.description = description;
    }
}
