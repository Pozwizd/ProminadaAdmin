package com.pozwizd.prominadaadmin.entity.property.enums;

import lombok.Getter;

@Getter
public enum ConditionFlat {
    UNDER_CONSTRUCTION("От строителей"),
    AFTER_RENOVATION("После ремонта"),
    NEEDS_RENOVATION("Требует ремонта");

    private String name;

    ConditionFlat(String name) {
        this.name = name;
    }
}
