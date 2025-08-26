package com.pozwizd.prominadaadmin.models.property.investor.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Request for {@link com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyFile}
 */
@Data
public class InvestorPropertyFileRequest implements Serializable {
    Long id;
    String name;
    MultipartFile path;
}