package com.pozwizd.prominadaadmin.entity.property.enums;

public enum ConditionBuilding {

    RESIDENTIAL("Жилое"),
    NONRESIDENTIAL("Нежилое"),
    COMMERCIAL("Коммерческое");

    private final String description;

    ConditionBuilding(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
