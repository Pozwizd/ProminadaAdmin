package com.pozwizd.prominadaadmin.entity.property.enums;


public enum CarpentryCondition {

    NEW("Новая"),
    OLD("Старая"),
    CUSTOM("Индивидуальная"),
    NONE("Отсутствует");

    private final String displayName;

    CarpentryCondition(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}