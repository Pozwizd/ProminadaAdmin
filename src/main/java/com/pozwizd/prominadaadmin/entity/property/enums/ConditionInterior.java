package com.pozwizd.prominadaadmin.entity.property.enums;

public enum ConditionInterior {

    FromBuilders("От строителей"),
    FromOwners("От владельцев");

    private final String description;

    ConditionInterior(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
