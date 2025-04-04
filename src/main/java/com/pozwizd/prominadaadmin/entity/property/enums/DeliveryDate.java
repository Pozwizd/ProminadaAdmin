package com.pozwizd.prominadaadmin.entity.property.enums;

public enum DeliveryDate {
    UNDER_CONSTRUCTION("Строится"),
    PARTIALLY_DELIVERED("В процессе сдачи"),
    READY_FOR_OCCUPANCY("Готово к заселению"),
    COMPLETED("Дом сдан");

    private String name;

    DeliveryDate(String name) {
        this.name = name;
    }
}
