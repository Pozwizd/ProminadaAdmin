package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum AirConditioner {

    ALL_ROOMS("Во всех комнатах"),
    NOT_ALL_ROOMS("Не во всех комнатах");


    private final String description;

    AirConditioner(String description) {
        this.description = description;
    }
}
