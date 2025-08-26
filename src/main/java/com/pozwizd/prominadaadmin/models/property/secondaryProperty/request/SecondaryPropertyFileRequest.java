package com.pozwizd.prominadaadmin.models.property.secondaryProperty.request;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyFile;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Request for {@link SecondaryPropertyFile}
 */
@Data
public class SecondaryPropertyFileRequest implements Serializable {
    Long id;
    String name;
    MultipartFile filePath;
}