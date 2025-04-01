package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum Gas {

    NATURAL_GAS("Природный газ"),
    LIQUID_GAS("Сжиженный газ"),
    NO_GAS("Отсутствует");

    private final String description;

    Gas(String description) {
        this.description = description;
    }

}
