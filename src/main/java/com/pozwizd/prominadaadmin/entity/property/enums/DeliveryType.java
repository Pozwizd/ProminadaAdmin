package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum DeliveryType {
    UNDER_CONSTRUCTION("deliveryType.underConstruction"),
    PARTIALLY_DELIVERED("deliveryType.partiallyDelivered"),
    READY_FOR_OCCUPANCY("deliveryType.readyForOccupancy"),
    COMPLETED("deliveryType.completed");
    private final String messageKey;
    DeliveryType(String messageKey) {
        this.messageKey = messageKey;
    }
    public static DeliveryType fromMessageKey(String key) {
        for (DeliveryType type : values()) {
            if (type.getMessageKey().equals(key)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No DeliveryType with messageKey: " + key);
    }
}
