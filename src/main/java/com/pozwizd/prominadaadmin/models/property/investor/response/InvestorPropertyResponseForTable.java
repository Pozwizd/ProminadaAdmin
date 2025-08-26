package com.pozwizd.prominadaadmin.models.property.investor.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * Response table for {@link com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorProperty}
 */
@Data
public class InvestorPropertyResponseForTable {

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
