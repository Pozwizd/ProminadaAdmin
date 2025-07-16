package com.pozwizd.prominadaadmin.filter;

import lombok.Data;

import java.util.List;


@Data
public class ResidentialLandFilterRequest {
    private Integer page = 0;
    private Integer size = 10;
    private List<Integer> districtIds;
    private List<Integer> topozoneIds;
    private Integer lastCommunication;
    private String street;
    private Boolean countRoom1;
    private Boolean countRoom2;
    private Boolean countRoom3;
    private Boolean countRoom4;
    private String floorsFrom;
    private String floorsTo;
    private Double priceFrom;
    private Double priceTo;
    private Double totalAreaFrom;
    private Double totalAreaTo;
    private Double livingAreaFrom;
    private Double livingAreaTo;
}