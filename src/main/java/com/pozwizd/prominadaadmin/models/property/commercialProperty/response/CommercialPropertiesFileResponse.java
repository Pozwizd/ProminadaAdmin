package com.pozwizd.prominadaadmin.models.property.commercialProperty.response;

import com.pozwizd.prominadaadmin.entity.property.commercial.CommercialPropertiesFile;
import lombok.Data;

import java.io.Serializable;

/**
 * Response for {@link CommercialPropertiesFile}
 */
@Data
public class CommercialPropertiesFileResponse implements Serializable {
    Long id;
    String name;
    String path;
}