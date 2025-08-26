package com.pozwizd.prominadaadmin.models.property.secondaryProperty.request;

import com.pozwizd.prominadaadmin.entity.property.secondaryProperty.SecondaryPropertyGalleryImage;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Request for {@link SecondaryPropertyGalleryImage}
 */
@Data
public class SecondaryPropertyGalleryImageRequest implements Serializable {
    Long id;
    String name;
    MultipartFile pathImage;
}