package com.pozwizd.prominadaadmin.entity.property.enums;

public enum PublicationStatus {

    PUBLICATED("Опубликовано"),
    DRAFT("Черновик"),
    ARCHIVED("Архивировано");

    private final String description;

    PublicationStatus(String description) {
        this.description = description;
    }

}
