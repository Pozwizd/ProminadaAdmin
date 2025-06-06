package com.pozwizd.prominadaadmin.models.builderProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuilderForView {
    private Long id;
    private String name;
    private String district;
    private String topozone;
    private String street;
    private String roof;
    private Boolean promotion;
    private String description;
    private String pathToImage;
    private String pathToChessPlanFile;
    private String pathToMortgageConditionsFile;
    private String pathToPriceFile;
    private String company;
}
