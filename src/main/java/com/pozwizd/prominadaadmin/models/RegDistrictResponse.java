package com.pozwizd.prominadaadmin.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegDistrictResponse {
    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("name_uk")
    private String nameUk;

    @JsonProperty("name_ru")
    private String nameRu;

    @JsonProperty("name_en")
    private String nameEn;

    @JsonProperty("katottg")
    private String katottg;
}
