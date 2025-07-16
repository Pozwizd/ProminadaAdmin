package com.pozwizd.prominadaadmin.models.banner;

import com.pozwizd.prominadaadmin.entity.ImageBanner;
import lombok.Data;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Request for {@link ImageBanner}
 */
@Data
public class ImageBannerRequest implements Serializable {
    Long id;
    String name;
    Integer priority;
    MultipartFile pathImage;
}