package com.pozwizd.prominadaadmin.models.property.investor.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Request for {@link com.pozwizd.prominadaadmin.entity.property.investorProperty.InvestorPropertyGalleryImage}
 */
@Data
public class InvestorPropertyGalleryImageRequest implements Serializable {
    Long id;
    String name;
    MultipartFile pathImage;
}