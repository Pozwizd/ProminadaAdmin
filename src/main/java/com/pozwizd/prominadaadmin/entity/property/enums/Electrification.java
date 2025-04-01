package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;



@Getter
public enum Electrification {
    V380("380V"),
    V220("220V"),
    NO_ELECTRIFICATION("Нет");

    private final String description;

    Electrification(String description) {
        this.description = description;
    }
}