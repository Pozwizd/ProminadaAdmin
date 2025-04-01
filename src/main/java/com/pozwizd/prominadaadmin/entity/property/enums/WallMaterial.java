package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum WallMaterial {

    BRICK("Кирпичный"),
    MONOLITHIC("Монолитный"),
    PANEL("Панельный");

    private final String value;

    WallMaterial(String value) {
        this.value = value;
    }
}
