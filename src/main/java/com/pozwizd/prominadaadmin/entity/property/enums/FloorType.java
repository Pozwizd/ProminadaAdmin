package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum FloorType {
    STYAZHKA("Стяжка"),
    TILE("Плитка"),
    PARQUET("Паркет"),
    LINOLEUM("Линолеум"),
    BRICK("Кирпич"),
    LAMINATE("Ламинат");

    private final String description;

    FloorType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}