package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum Kitchen {

    STANDARD("Стандартная"),
    STUDIO("Студия"),
    NO_KITCHEN("Отсутствует");
    private final String value;

    Kitchen(String value) {
        this.value = value;
    }
}
