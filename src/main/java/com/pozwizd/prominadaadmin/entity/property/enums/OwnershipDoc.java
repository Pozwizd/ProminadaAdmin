package com.pozwizd.prominadaadmin.entity.property.enums;


public enum OwnershipDoc {
    PURCHASE_AGREEMENT("Договор купли-продажи"),
    OWNERSHIP_CERTIFICATE("Свидетельство о владении");

    private final String description;

    OwnershipDoc(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
