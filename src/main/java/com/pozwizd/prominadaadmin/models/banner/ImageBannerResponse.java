package com.pozwizd.prominadaadmin.models.banner;

import com.pozwizd.prominadaadmin.entity.ImageBanner;
import lombok.Data;

import java.io.Serializable;


/**
 * Response for {@link ImageBanner}
 */
@Data
public class ImageBannerResponse {
    Long id;
    String name;
    Integer priority;
    String pathImage;
}