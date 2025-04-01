package com.pozwizd.prominadaadmin.entity.property.enums;


import lombok.Getter;

@Getter
public enum EntranceDoor {

    ARMORED("Бронированная"),
    UNARMORED("Небронированная"),
    LOCKED("Закрытая"),
    UNLOCKED("Открытая");
    private final String description;
    EntranceDoor(String description) {
        this.description = description;
    }

}
