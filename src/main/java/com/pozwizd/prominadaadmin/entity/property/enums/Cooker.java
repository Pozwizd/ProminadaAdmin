package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum Cooker {

    ELECTRIC("Электрическая"),

    GAS("Газовая");


    private String name;
    Cooker(String name) {
        this.name = name;
    }
}
