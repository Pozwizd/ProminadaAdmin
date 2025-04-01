package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum Ventilation {

    Exhausted("Вытяжка"),
    NO_EXHAUST("Отсутствует");

    private final String description;

    Ventilation(String description) {
        this.description = description;
    }
}
