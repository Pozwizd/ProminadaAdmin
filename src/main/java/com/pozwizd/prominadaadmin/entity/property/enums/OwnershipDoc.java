package com.pozwizd.prominadaadmin.entity.property.enums;


import lombok.Getter;

@Getter
public enum OwnershipDoc {
    PURCHASE_AGREEMENT("Договор купли-продажи"),
    OWNERSHIP_CERTIFICATE("Свидетельство о владении");

    private final String description;

    OwnershipDoc(String description) {
        this.description = description;
    }

}
