package com.pozwizd.prominadaadmin.entity.property.enums;

public enum TypeWindows {

    METOPLASTIC("Метопластик"),
    PVC("Пластиковые окна"),
    WOODEN("Деревянные окна"),
    ALUMINUM("Алюминиевые окна"),
    GLASS_BLOCK("Стеклоблоки"),
    WITHOUT_WINDOW("Без окна");

    private final String description;

    // Конструктор
    TypeWindows(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}