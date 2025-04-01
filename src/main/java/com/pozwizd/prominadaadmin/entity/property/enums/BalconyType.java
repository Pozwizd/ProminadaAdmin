package com.pozwizd.prominadaadmin.entity.property.enums;


import lombok.Getter;

@Getter
public enum BalconyType {

    BALCONY("Балкон"),
    LOGGIA("Логгия"),
    NONE("Нет");

    private String name;

    BalconyType(String name) {
        this.name = name;
    }
}
