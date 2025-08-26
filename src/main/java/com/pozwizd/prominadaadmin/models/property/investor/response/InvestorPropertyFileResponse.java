package com.pozwizd.prominadaadmin.models.property.investor.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Response for {@link com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyFile}
 */
@Data
public class InvestorPropertyFileResponse implements Serializable {
    Long id;
    String name;
    String pathImage;
}