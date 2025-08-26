package com.pozwizd.prominadaadmin.models.property.commercialProperty.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommercialPropertiesResponseForTable {

    private Long id;
    private LocalDate lastCommunication;
    private String objectCode;
    private String region;
    private String city;
    private String district;
    private String street;
    private String topozone;
    private String totalRooms;
    private String floor;
    private String totalFloor;
    private String totalArea;
    private String price;
    private Boolean isAdvertising;
    private Boolean hasPhoto;

}
