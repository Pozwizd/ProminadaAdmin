package com.pozwizd.prominadaadmin.models.property.commercialProperty.request;

import com.pozwizd.prominadaadmin.entity.property.commercialProperty.CommercialPropertiesFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Request for {@link CommercialPropertiesFile}
 */
@Data
public class CommercialPropertiesFileRequest implements Serializable {
    Long id;
    String name;
    MultipartFile path;
}