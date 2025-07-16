package com.pozwizd.prominadaadmin.models.property.secondaryProperty.response;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyFile;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * Response for {@link SecondaryPropertyFile}
 */
@Data
public class SecondaryPropertyFileResponse implements Serializable {
    Long id;
    String name;
    String path;
}