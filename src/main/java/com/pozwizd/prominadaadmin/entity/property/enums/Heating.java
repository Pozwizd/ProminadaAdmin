package com.pozwizd.prominadaadmin.entity.property.enums;

public enum Heating {

    CENTRALIZED("Централизованная"),
    AUTONOMOUS("Автономная");

    private final String description;

    Heating(String description) {
        this.description = description;
    }
}
