package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum TypePropertySecondary {

    INDIVIDUAL("Отдельная"),
    COMMUNAL("Комунальная");

    private String name;

    TypePropertySecondary(String name) {
        this.name = name;
    }

}
