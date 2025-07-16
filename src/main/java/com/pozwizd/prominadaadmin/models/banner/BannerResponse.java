package com.pozwizd.prominadaadmin.models.banner;

import com.pozwizd.prominadaadmin.entity.ImageBanner;
import lombok.Data;

import java.util.List;

/**
 * Response for {@link ImageBanner}
 */
@Data
public class BannerResponse {
    Long id;
    String name;
    Boolean status;
    List<ImageBannerResponse> imageBannersRequest;
}
