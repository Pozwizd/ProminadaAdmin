package com.pozwizd.prominadaadmin.entity.property.enums;

public enum RoofType {

    METAL("Металлический"),

    SHINGLE("Черепичный"),

    STONE("Каменный");

    private final String value;

    RoofType(String value) {
        this.value = value;
    }
}
