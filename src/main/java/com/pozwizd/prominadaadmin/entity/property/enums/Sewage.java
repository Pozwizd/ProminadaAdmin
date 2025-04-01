package com.pozwizd.prominadaadmin.entity.property.enums;

public enum Sewage {

    CENTRALIZED("Централизованная"),
    AUTONOMOUS("Автономная"),
    NO_SEWAGE("Отсутствует");

    private final String description;

    Sewage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
