package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum DesignatedUseOfLand {

    RESIDENTIAL("Жилое"),
    COMMERCIAL("Коммерческое"),
    INDUSTRIAL("Промышленное");
    private final String value;

    DesignatedUseOfLand(String value) {
        this.value = value;
    }
}
