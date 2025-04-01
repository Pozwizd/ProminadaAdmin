package com.pozwizd.prominadaadmin.entity.property.enums;

public enum WaterSupply {

    CENTRALIZED("Централизованная"),
    AUTONOMOUS("Автономная");

    private final String description;

    WaterSupply(String description) {
        this.description = description;
    }
}
