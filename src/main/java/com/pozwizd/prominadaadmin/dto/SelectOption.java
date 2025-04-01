package com.pozwizd.prominadaadmin.dto;

/**
 * Data Transfer Object (DTO) для представления одной опции
 * в HTML-элементе &lt;select&gt;.
 * Используется для передачи данных между бэкендом и фронтендом в формате JSON.
 */
public class SelectOption {
    /**
     * Значение опции (атрибут value тега &lt;option&gt;).
     * Обычно это системное имя (например, имя константы enum).
     */
    private String value;
    /**
     * Отображаемый текст опции (содержимое тега &lt;option&gt;).
     * Это локализованный текст, который видит пользователь.
     */
    private String label;

    /**
     * Конструктор для создания объекта опции.
     * @param value Системное значение опции.
     * @param label Отображаемый локализованный текст.
     */
    public SelectOption(String value, String label) {
        this.value = value;
        this.label = label;
    }

    // --- Геттеры ---
    // Геттеры необходимы для корректной сериализации объекта в JSON библиотекой Jackson.

    /**
     * Возвращает системное значение опции.
     * @return Значение опции (value).
     */
    public String getValue() {
        return value;
    }

    /**
     * Возвращает отображаемый локализованный текст опции.
     * @return Текст опции (label).
     */
    public String getLabel() {
        return label;
    }

    // --- Сеттеры ---
    // Сеттеры не обязательны для сериализации, но могут быть полезны.

    /**
     * Устанавливает системное значение опции.
     * @param value Новое значение опции.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Устанавливает отображаемый текст опции.
     * @param label Новый текст опции.
     */
    public void setLabel(String label) {
        this.label = label;
    }
}