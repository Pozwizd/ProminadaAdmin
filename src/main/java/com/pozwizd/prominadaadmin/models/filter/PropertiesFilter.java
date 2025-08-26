package com.pozwizd.prominadaadmin.models.filter;

import lombok.Data;

import java.util.List;

@Data
public class PropertiesFilter {
    Integer page = 0;
    Integer size = 10;
    List<Integer> regionIds;
    List<Integer> cityIds;
    List<Integer> districtIds;
    List<Integer> topozoneIds;
    Integer lastCommunication;
    String street;
    Boolean countRoom1;
    Boolean countRoom2;
    Boolean countRoom3;
    Boolean countRoom4;
    String floorsFrom;
    String floorsTo;
    Double priceFrom;
    Double priceTo;
    Double totalAreaFrom;
    Double totalAreaTo;
    Double livingAreaFrom;
    Double livingAreaTo;
}
